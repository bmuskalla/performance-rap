package org.eclipse.rap.rwt.performance.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.eclipse.rap.rwt.performance.IPerformanceStorage;
import org.eclipse.rap.rwt.performance.result.ITestExecutionResult;

public class H2PerformanceStorage implements IPerformanceStorage {

  private static final String JDBC_URI = "jdbc:h2:tcp://localhost/~/performance";
  private static final String USERNAME = "sa";
  private static final String PASSWORD = "";
  private Connection con;

  public void putResults( TestCase test, long[] frames ) {
    int testId = putTest( test );
    int runId = putTestRun( testId );
    putFrames( runId, frames );
  }

  private void putFrames( int runId, long[] frames ) {
    for( int i = 0; i < frames.length; i++ ) {
      long frameTime = frames[ i ];
      putFrame( runId, frameTime );
    }
  }

  private void putFrame( int runId, long frameTime ) {
    try {
      Connection con = getConnection();
      String sql = "INSERT INTO iterations( TESTRUNID, RESULT) VALUES (?, ?);";
      PreparedStatement stmt = con.prepareStatement( sql );
      stmt.setInt( 1, runId );
      stmt.setLong( 2, frameTime );
      stmt.execute();
    } catch( SQLException e ) {
      e.printStackTrace();
    }
  }

  private int putTestRun( int testId ) {
    Connection con = getConnection();
    int runId = 0;
    try {
      String sql = "INSERT INTO testruns( TESTID , DATE) VALUES (?, NOW());";
      PreparedStatement stmt = con.prepareStatement( sql );
      stmt.setInt( 1, testId );
      stmt.execute();
      runId = getLastInsertId( stmt );
    } catch( SQLException e ) {
      e.printStackTrace();
    }
    return runId;
  }

  private int putTest( TestCase test ) {
    Connection con = getConnection();
    String className = test.getClass().getName();
    String testName = test.getName();
    int testId = 0;
    try {
      String sql = "INSERT INTO tests( TESTNAME , TESTCLASS) VALUES (?, ?);";
      PreparedStatement stmt = con.prepareStatement( sql );
      stmt.setString( 1, testName );
      stmt.setString( 2, className );
      stmt.execute();
      testId = getLastInsertId( stmt );
    } catch( SQLException e ) {
      e.printStackTrace();
    }
    return testId;
  }

  private int getLastInsertId( PreparedStatement stmt ) throws SQLException {
    ResultSet keys = stmt.getGeneratedKeys();
    keys.next();
    return keys.getInt( 1 );
  }

  private Connection getConnection() {
    Connection result = null;
    if( con != null ) {
      result = con;
    } else {
      try {
        result = DriverManager.getConnection( JDBC_URI, USERNAME, PASSWORD );
      } catch( SQLException e ) {
        e.printStackTrace();
      }
    }
    return result;
  }

  public H2PerformanceStorage() {
    try {
      Class.forName( "org.h2.Driver" );
    } catch( ClassNotFoundException e ) {
      e.printStackTrace();
    }
  }

  public ITestExecutionResult[] getAggregatedResults() {
    throw new UnsupportedOperationException();
  }

  public void dispose() throws Exception {
    if( con != null ) {
      con.close();
    }
  }
}
