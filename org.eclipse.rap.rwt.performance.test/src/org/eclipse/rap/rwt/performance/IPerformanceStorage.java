package org.eclipse.rap.rwt.performance;

import java.util.List;

import junit.framework.TestCase;

public interface IPerformanceStorage {

  public void putResults( TestCase test, List frames );

  public ITestExecutionResult[] getAggregatedResults();
  
}
