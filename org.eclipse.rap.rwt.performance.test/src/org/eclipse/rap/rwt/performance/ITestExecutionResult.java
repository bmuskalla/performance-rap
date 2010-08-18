package org.eclipse.rap.rwt.performance;


public interface ITestExecutionResult {

  public String getName();

  public void addIteration( long ms );

  public long computeSum();

  public long[] getIterations();
  
}
