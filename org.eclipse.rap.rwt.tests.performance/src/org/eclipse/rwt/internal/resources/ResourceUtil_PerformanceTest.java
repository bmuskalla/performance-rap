package org.eclipse.rwt.internal.resources;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.rwt.performance.PerformanceTestCase;

public class ResourceUtil_PerformanceTest extends PerformanceTestCase {

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
    measuredRun( testable, 100 );
    assertPerformance();
  }
}
