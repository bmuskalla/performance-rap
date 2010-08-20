package org.eclipse.rap.rwt.performance.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.eclipse.rap.rwt.performance.IPerformanceStorage;
import org.eclipse.rap.rwt.performance.result.ITestExecutionResult;
import org.eclipse.rap.rwt.performance.result.TestExecutionResult;

public class FilePerformanceStorage implements IPerformanceStorage {

  private static final String SEPARATOR = ";";
  private String FILE_NAME = System.getProperty( "user.home" ) + "/results.csv";

  public void putResults( final TestCase test, final long[] frames ) {
    Writer out = null;
    try {
      FileWriter writer = new FileWriter( FILE_NAME, true );
      out = new BufferedWriter( writer );
      writeResult( out, test, frames );
    } catch( Exception e ) {
      throw new RuntimeException( e );
    } finally {
      try {
        out.close();
      } catch( IOException e ) {
        throw new RuntimeException( e );
      }
    }
  }

  private void writeResult( Writer out, TestCase test, long[] frames )
    throws IOException
  {
    String className = test.getClass().getName();
    String testName = className + "." + test.getName();
    writeTestname( out, testName );
    writeSeparator( out );
    writeResults( out, frames );
    out.write( "\n" );
  }

  private void writeTestname( Writer out, String testName ) throws IOException {
    out.write( testName );
  }

  private void writeSeparator( Writer out ) throws IOException {
    out.write( SEPARATOR );
  }

  private void writeResults( Writer out, long[] frames ) throws IOException {
    for( int i = 0; i < frames.length; i++ ) {
      long frameTime = frames[ i ];
      out.write( String.valueOf( getTimeInMilliSeconds( frameTime ) ) );
      writeSeparator( out );
    }
  }

  private long getTimeInMilliSeconds( long frameTime ) {
    return frameTime / 1000 / 1000;
  }

  private String[] getFileContents() {
    List<String> lines = new ArrayList<String>();
    try {
      FileReader fr = new FileReader( FILE_NAME );
      BufferedReader br = new BufferedReader( fr );
      String s;
      while( ( s = br.readLine() ) != null ) {
        lines.add( s );
      }
    } catch( Exception e ) {
      throw new RuntimeException( e );
    }
    return ( String[] )lines.toArray( new String[ lines.size() ] );
  }

  public ITestExecutionResult[] getAggregatedResults() {
    String[] lines = getFileContents();
    Map<String, ITestExecutionResult> results = new HashMap<String, ITestExecutionResult>();
    for( int l = 0; l < lines.length; l++ ) {
      String string = lines[ l ];
      String[] columns = string.split( SEPARATOR );
      String testName = columns[ 0 ];
      ITestExecutionResult result;
      if( results.containsKey( testName ) ) {
        result = ( ITestExecutionResult )results.get( testName );
      } else {
        result = new TestExecutionResult( testName );
      }
      long sum = 0;
      for( int i = 1; i < columns.length; i++ ) {
        String frame = columns[ i ];
        long iterationTime = Long.valueOf( frame ).longValue();
        sum = sum + iterationTime;
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
