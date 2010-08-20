package org.eclipse.rap.rwt.performance;

import org.eclipse.rwt.Fixture;

import junit.framework.TestCase;

public class PerformanceTestCase extends TestCase {

  protected PerformanceStopWatch watch;

  protected void setUp() throws Exception {
    Fixture.setUp();
    watch = new PerformanceStopWatch( this );
  }

  protected void tearDown() throws Exception {
    Fixture.tearDown();
  }

  public void measuredRun( Runnable setup, Runnable testable, int times ) {
    for( int i = 0; i < times; i++ ) {
      setup.run();
      watch.start();
      testable.run();
      watch.stop();
      watch.commitFrame();
    }
    watch.commit();
  }

  
  public void measuredRun( Runnable testable, int times ) {
    Runnable nullRunnable = new Runnable() {

      public void run() {
      }
    };
    measuredRun( nullRunnable, testable, times );
  }
  
  public void assertPerformance() {
    // TODO
    IPerformanceStorage storage = StorageFactory.createPerformanceStorage();
    storage.getAggregatedResults();
  }
}
