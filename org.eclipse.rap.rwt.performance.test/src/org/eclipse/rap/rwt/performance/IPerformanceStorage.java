package org.eclipse.rap.rwt.performance;

import junit.framework.TestCase;

import org.eclipse.rap.rwt.performance.result.ITestExecutionResult;

/**
 * Storage for test results.
 * 
 * Will be created by the {@link StorageFactory} for outside use, everything
 * unless the default constructor will be ignored.
 * 
 * Each storage is responsible to dispose acquired resources
 * upon {@link #dispose()}.
 *
 */
public interface IPerformanceStorage {

  /**
   * Puts all frame times into this storage, grouped by the testcase at hand.
   * It is up to the storage implementation to store all frame times or only
   * store informations that are required to fulfill the
   * {@link ITestExecutionResult} contract.
   * 
   * @param test the current testcase
   * @param frames list of frame times in nanoseconds
   */
  public void putResults( TestCase test, long[] frames );

  /**
   * Returns the aggregated results for all test runs in this storage.
   * 
   * @return list of {@link ITestExecutionResult}s
   */
  public ITestExecutionResult[] getAggregatedResults();
  
  /**
   * Releases all resources that this storage may have been acquired such as
   * file locks or database connections.
   * 
   * @throws Exception
   */
  public void dispose() throws Exception;
  
}
