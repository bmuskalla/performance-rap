package org.eclipse.rap.rwt.performance;

import org.eclipse.rap.rwt.performance.file.StdOutPerformanceStorage;

public class StorageFactory {

  private static final String STORAGE_PROPERTY = StorageFactory.class.getName();
  private static IPerformanceStorage storage;

  public static IPerformanceStorage createPerformanceStorage() {
    if( storage == null ) {
      try {
        return loadStorageFactory();
      } catch( Exception e ) {
        throw new RuntimeException( e );
      }
    }
    return storage;
  }

  private static IPerformanceStorage loadStorageFactory()
    throws ClassNotFoundException, InstantiationException,
    IllegalAccessException
  {
    IPerformanceStorage instance;
    String storageClazz = System.getProperty( STORAGE_PROPERTY );
    if( storageClazz == null ) {
      storageClazz = StdOutPerformanceStorage.class.getName();
      Class<?> storageClass = Class.forName( storageClazz );
      instance = ( IPerformanceStorage )storageClass.newInstance();
    } else {
      String[] clazzes = storageClazz.split( "," );
      IPerformanceStorage[] storages = new IPerformanceStorage[ clazzes.length ];
      for( int i = 0; i < clazzes.length; i++ ) {
        String clazz = clazzes[ i ];
        Class<?> storageClass = Class.forName( clazz );
        storages[ i ] = ( IPerformanceStorage )storageClass.newInstance();
      }
      instance = new CompositePerformanceStorage( storages );
    }
    return instance;
  }
}
