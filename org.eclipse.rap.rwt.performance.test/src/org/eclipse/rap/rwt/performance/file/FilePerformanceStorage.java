package org.eclipse.rap.rwt.performance.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.rap.rwt.performance.IPerformanceStorage;

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
          results.add( Long.valueOf( sum ) );
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
}
