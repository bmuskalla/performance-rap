package org.eclipse.rap.rwt.performance;

import java.util.ArrayList;
import java.util.List;

public class TestExecutionResult implements ITestExecutionResult {

  private String name;
  private List iterations;

  public TestExecutionResult( String testName ) {
    name = testName;
    iterations = new ArrayList();
  }

  public String getName() {
    return name;
  }

  public void addIteration( long ms ) {
    iterations.add( Long.valueOf( ms ) );
  }

  public long computeSum() {
    long sum = 0;
    for( int i = 0; i < iterations.size(); i++ ) {
      Long iterationTime = ( Long )iterations.get( i );
      sum = sum + iterationTime.longValue();
    }
    return sum;
  }

  public long[] getIterations() {
    long[] result = new long[ iterations.size() ];
    for( int i = 0; i < iterations.size(); i++ ) {
      Long iteration = ( Long )iterations.get( i );
      result[ i ] = iteration.longValue();
    }
    return result;
  }
}
