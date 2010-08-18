package org.eclipse.rap.rwt.performance.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
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

  private String FILE_NAME = "/home/bmuskalla/Desktop/results.txt";

  public void putResults( final TestCase test, final List frames ) {
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
      }
    }
  }

  private void writeResult( Writer out, TestCase test, List frames )
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
    out.write( "|" );
  }

  private void writeResults( Writer out, List frames ) throws IOException {
    for( int i = 0; i < frames.size(); i++ ) {
      Long frameTime = ( Long )frames.get( i );
      out.write( String.valueOf( frameTime ) );
      writeSeparator( out );
    }
  }

  public List getAggregatedResults( String testName ) {
    List results = new ArrayList();
    try {
      FileReader fr = new FileReader( FILE_NAME );
      BufferedReader br = new BufferedReader( fr );
      String s;
      while( ( s = br.readLine() ) != null ) {
        if( s.startsWith( testName ) ) {
          String[] values = s.split( "\\|" );
          long sum = 0;
          for( int i = 1; i < values.length; i++ ) {
            String frame = values[ i ];
            sum = sum + Long.valueOf( frame ).longValue();
          }
          results.add( Long.valueOf( sum / values.length - 1 ) );
        }
      }
      fr.close();
    } catch( FileNotFoundException e ) {
      e.printStackTrace();
    } catch( IOException e ) {
      e.printStackTrace();
    }
    return results;
  }

  private String[] getFileContents() {
    List lines = new ArrayList();
    try {
      FileReader fr = new FileReader( FILE_NAME );
      BufferedReader br = new BufferedReader( fr );
      String s;
      while( ( s = br.readLine() ) != null ) {
        lines.add( s );
      }
    } catch( Exception e ) {
      e.printStackTrace();
    }
    return ( String[] )lines.toArray( new String[ lines.size() ] );
  }

  public ITestExecutionResult[] getAggregatedResults() {
    String[] lines = getFileContents();
    Map results = new HashMap();
    for( int i1 = 0; i1 < lines.length; i1++ ) {
      String string = lines[ i1 ];
      String[] columns = string.split( "\\|" );
      String testName = columns[ 0 ];
      
      ITestExecutionResult result;
      if( results.containsKey( testName )) {
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
    return (org.eclipse.rap.rwt.performance.result.ITestExecutionResult[] )results.values().toArray( new ITestExecutionResult[ 0 ] );
  }
}
