package org.eclipse.rap.rwt.performance;

import junit.framework.TestCase;

import org.eclipse.rap.rwt.performance.result.ITestExecutionResult;

public class CompositePerformanceStorage implements IPerformanceStorage {

  private IPerformanceStorage[] internalStorages;

  public CompositePerformanceStorage( IPerformanceStorage[] storages ) {
    internalStorages = storages;
  }

  public void putResults( TestCase test, long[] frames ) {
    for( int i = 0; i < internalStorages.length; i++ ) {
      IPerformanceStorage storage = internalStorages[ i ];
      storage.putResults( test, frames );
    }
  }

  public ITestExecutionResult[] getAggregatedResults() {
    for( int i = 0; i < internalStorages.length; i++ ) {
      IPerformanceStorage storage = internalStorages[ i ];
      storage.getAggregatedResults();
    }
    return new ITestExecutionResult[0];
  }

  public void dispose() throws Exception {
    for( int i = 0; i < internalStorages.length; i++ ) {
      IPerformanceStorage storage = internalStorages[ i ];
      storage.dispose();
    }
  }
}
