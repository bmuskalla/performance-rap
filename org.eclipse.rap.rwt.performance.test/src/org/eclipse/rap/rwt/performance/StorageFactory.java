package org.eclipse.rap.rwt.performance;

import org.eclipse.rap.rwt.performance.file.FilePerformanceStorage;

public class StorageFactory {

  public static IPerformanceStorage createPerformanceStorage() {
    return new FilePerformanceStorage();
//    return new JUnitPerformanceStorage();
  }
}
