package org.eclipse.rap.rwt.performance;

import java.util.List;

import org.eclipse.rap.rwt.performance.result.ITestExecutionResult;

import junit.framework.TestCase;

public interface IPerformanceStorage {

  public void putResults( TestCase test, List frames );

  public ITestExecutionResult[] getAggregatedResults();
  
}
