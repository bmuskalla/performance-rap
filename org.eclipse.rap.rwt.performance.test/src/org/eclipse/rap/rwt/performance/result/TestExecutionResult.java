package org.eclipse.rap.rwt.performance.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestExecutionResult implements ITestExecutionResult {

  private String name;
  private List<Long> iterations;

  public TestExecutionResult( String testName ) {
    name = testName;
    iterations = new ArrayList<Long>();
  }

  public String getName() {
    return name;
  }

  public void addIteration( long ms ) {
    iterations.add( new Long( ms ) );
  }

  public long computeAverage() {
    long sum = 0;
    for( int i = 0; i < iterations.size(); i++ ) {
      Long iterationTime = ( Long )iterations.get( i );
      sum = sum + iterationTime.longValue();
    }
    return getTimeinMilliseconds( sum / iterations.size() );
  }

  private long getTimeinMilliseconds( long l ) {
    return l / 1000 / 1000;
  }

  public long[] getIterations() {
    long[] result = new long[ iterations.size() ];
    for( int i = 0; i < iterations.size(); i++ ) {
      Long iteration = ( Long )iterations.get( i );
      result[ i ] = iteration.longValue();
    }
    return result;
  }

  public long computeMedian() {
    Long[] sortedList = ( Long[] )iterations.toArray( new Long[ 0 ] );
    Arrays.sort( sortedList );
    int median = sortedList.length / 2;
    long result;
    long rightValue = sortedList[ median ].longValue();
    if( sortedList.length % 2 == 1 ) {
      result = rightValue;
    } else {
      long leftValue = sortedList[ median - 1 ].longValue();
      result = ( leftValue + rightValue ) / 2;
    }
    return getTimeinMilliseconds( result );
  }
}
