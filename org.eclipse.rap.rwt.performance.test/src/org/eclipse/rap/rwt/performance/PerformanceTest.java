package org.eclipse.rap.rwt.performance;

import org.eclipse.rwt.Fixture;

import junit.framework.TestCase;

public class PerformanceTest extends TestCase {

  protected PerformanceMeter meter;

  protected void setUp() throws Exception {
    Fixture.setUp();
    meter = new PerformanceMeter( this );
  }

  protected void tearDown() throws Exception {
    Fixture.tearDown();
  }

  public void measuredRun( Runnable setup, Runnable testable, int times ) {
    for( int i = 0; i < times; i++ ) {
      setup.run();
      meter.start();
      testable.run();
      meter.stop();
      meter.commitFrame();
    }
  }

  public void measuredRun( Runnable testable, int times ) {
    Runnable nullRunnable = new Runnable() {

      public void run() {
      }
    };
    measuredRun( nullRunnable, testable, times );
  }
}
