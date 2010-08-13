package org.eclipse.rwt.internal.resources;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.rwt.performance.PerformanceTest;

public class ResourceUtil_PerformanceTest extends PerformanceTest {

  public void testReadBinary() throws Exception {
    Runnable testable = new Runnable() {

      public void run() {
        ClassLoader classLoader = getClass().getClassLoader();
        String imageFile = "resources/big_binary.png";
        InputStream stream = classLoader.getResourceAsStream( imageFile );
        try {
          ResourceUtil.readBinary( stream );
        } catch( IOException e ) {
          e.printStackTrace();
          fail();
        }
      }
    };
    measuredRun( testable, 1000 );
    meter.commit();
  }
}
