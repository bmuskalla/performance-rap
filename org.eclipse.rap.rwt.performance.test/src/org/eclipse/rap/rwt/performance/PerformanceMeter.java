package org.eclipse.rap.rwt.performance;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class PerformanceMeter {

  private long startTime;
  private long endTime;
  private List frames;
  private final TestCase testCase;

  public PerformanceMeter( TestCase test ) {
    frames = new ArrayList();
    testCase = test;
  }

  public void start() {
    startTime = System.nanoTime();
  }

  public void stop() {
    endTime = System.nanoTime();
  }

  public void commitFrame() {
    long measuredTime = endTime - startTime;
    frames.add( Long.valueOf( measuredTime ) );
  }

  public void commit() {
    long sum = 0;
    long min = Integer.MAX_VALUE;
    long max = 0;
    for( int i = 0; i < frames.size(); i++ ) {
      long frameTime = ( ( Long )frames.get( i ) ).longValue();
      System.out.println( "Frame " + i + ": " + formatTime( frameTime ) );
      sum = sum + frameTime;
      min = Math.min( min, frameTime );
      max = Math.max( max, frameTime );
    }
    System.out.println( "Testcase: " + testCase );
    System.out.println( "Average Time: " + formatTime( sum / frames.size() ) );
    System.out.println( "Min Time: " + formatTime( min ) );
    System.out.println( "Max Time: " + formatTime( max ) );
    System.out.println( "Total time: " + formatTime( sum ) );
    System.out.println( "" );
    IPerformanceStorage storage = StorageFactory.createPerformanceStorage();
    storage.putResults( testCase, frames );
  }

  private String formatTime( long time ) {
    long microSeconds = time / 1000;
    long milliSeconds = microSeconds / 1000;
    return milliSeconds + "ms";
  }
}
