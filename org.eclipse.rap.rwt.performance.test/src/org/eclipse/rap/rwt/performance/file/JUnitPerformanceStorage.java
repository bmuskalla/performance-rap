package org.eclipse.rap.rwt.performance.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

import org.eclipse.rap.rwt.performance.IPerformanceStorage;
import org.eclipse.rap.rwt.performance.result.ITestExecutionResult;

public class JUnitPerformanceStorage implements IPerformanceStorage {

  private String WORKSPACE = "/home/bmuskalla/.hudson/jobs/junit-performance/workspace/";

  public void putResults( final TestCase test, final List frames ) {
    Writer out = null;
    try {
      FileWriter writer = new FileWriter( WORKSPACE + getFileName( test ), true );
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

  private String getFileName( TestCase test ) {
    return "TEST-" + test.getClass().getName() + ".xml";
  }

  private void writeResult( Writer out, TestCase test, List frames )
    throws IOException
  {
    String testName = getClassName( test );
    writeHeader( out, testName );
    writeProperties( out );
    writeResults( out, test, frames );
    writeFooter( out );
    out.write( "\n" );
  }

  private void writeFooter( Writer out ) throws IOException {
    out.write( "</testsuite>" );
  }

  private String getClassName( TestCase test ) {
    String className = test.getClass().getName();
    String testName = className + "." + test.getName();
    return testName;
  }

  private void writeHeader( Writer out, String testName ) throws IOException {
    out.write( "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" );
    out.write( "<testsuite failures=\"0\" time=\""
               + 1000
               * Math.random()
               + "\" errors=\"0\" skipped=\"0\" tests=\"5\" name=\""
               + testName
               + "\">\n" );
  }

  private void writeProperties( Writer out ) throws IOException {
    Properties properties = System.getProperties();
    Enumeration enums = properties.propertyNames();
    for( ; enums.hasMoreElements(); ) {
      String name = ( String )enums.nextElement();
      String value = ( String )properties.get( name );
      out.write( "<property name=\"" + name + "\" value=\"" + value + "\"/>\n" );
    }
  }

  private void writeResults( Writer out, TestCase test, List frames )
    throws IOException
  {
    long sum = 0;
    for( int i = 0; i < frames.size(); i++ ) {
      Long frameTime = ( Long )frames.get( i );
      sum = ( ( frameTime.longValue() / 1000 ) / 1000 ) / 1000;
    }
    long avg = sum / frames.size();
    out.write( "<testcase time=\""
               + avg
               + "\" classname=\""
               + test.getClass().getName()
               + "\" name=\""
               + test.getName()
               + "\"/>\n" );
  }

  public ITestExecutionResult[] getAggregatedResults() {
    throw new UnsupportedOperationException();
  }

}
