package org.eclipse.rap.rwt.performance.file;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.rap.rwt.performance.IPerformanceStorage;
import org.eclipse.rap.rwt.performance.result.ITestExecutionResult;
import org.eclipse.rap.rwt.performance.result.TestExecutionResult;

public class StdOutPerformanceStorage implements IPerformanceStorage {

  Map<String, long[]> performanceResults = new HashMap<String, long[]>();

  public void putResults( final TestCase test, final long[] frames ) {
    String className = test.getClass().getName();
    String testName = className + "." + test.getName();
    performanceResults.put( testName, frames );
  }

  public ITestExecutionResult[] getAggregatedResults() {
    ITestExecutionResult[] testResults = getTestResults();
    int maxTestNameLength = getMaxTestNameLength( testResults );
    printHeader( maxTestNameLength );
    for( int i = 0; i < testResults.length; i++ ) {
      ITestExecutionResult result = testResults[ i ];
      System.out.print( result.getName() );
      printNameSeparator( maxTestNameLength, result.getName() );
      printSpaces( 3 );
      System.out.print( result.computeAverage() );
      printSpaces( 3 );
      System.out.println( result.computeMedian() );
    }
    return testResults;
  }

  private static int getMaxTestNameLength( ITestExecutionResult[] results ) {
    int maxLenght = 0;
    for( int i = 0; i < results.length; i++ ) {
      ITestExecutionResult result = results[ i ];
      String test = result.getName();
      maxLenght = Math.max( test.length(), maxLenght );
    }
    return maxLenght;
  }

  private void printHeader( int maxTestNameLength ) {
    System.out.print( "Testcase" );
    printNameSeparator( maxTestNameLength, "Testcase" );
    printSpaces( 3 );
    System.out.print( "Average" );
    printSpaces( 5 );
    System.out.println( "Median" );
    printChar( maxTestNameLength + 5 + 3 + "Average".length() + 3 + "Median".length(), "-" );
    System.out.println();
  }

  private static void printNameSeparator( int maxLength, String testcase ) {
    int rest = maxLength - testcase.length() + 5;
    printSpaces( rest );
  }

  private static void printSpaces( int n ) {
    printChar( n, " " );
  }

  private static void printChar( int n, String string ) {
    for( int i = 0; i < n; i++ ) {
      System.out.print( string );
    }
  }

  private ITestExecutionResult[] getTestResults() {
    Map<String, ITestExecutionResult> results = new HashMap<String, ITestExecutionResult>();
    Set<String> testNames = performanceResults.keySet();
    for( Iterator<String> iterator = testNames.iterator(); iterator.hasNext(); )
    {
      String testName = ( String )iterator.next();
      ITestExecutionResult result;
      result = new TestExecutionResult( testName );
      long sum = 0;
      long[] frameTimes = performanceResults.get( testName );
      for( int i = 0; i < frameTimes.length; i++ ) {
        long frame = frameTimes[ i ];
        sum = sum + frame;
      }
      result.addIteration( sum );
      results.put( testName, result );
    }
    return ( org.eclipse.rap.rwt.performance.result.ITestExecutionResult[] )results.values()
      .toArray( new ITestExecutionResult[ 0 ] );
  }

  public void dispose() throws Exception {
    // nothing to do
  }
}
