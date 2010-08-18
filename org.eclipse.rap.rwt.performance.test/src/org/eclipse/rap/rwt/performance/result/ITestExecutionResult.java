package org.eclipse.rap.rwt.performance.result;


public interface ITestExecutionResult {

  public String getName();

  public void addIteration( long ms );

  public long computeAverage();

  public long[] getIterations();

  public long computeMedian();
  
}
