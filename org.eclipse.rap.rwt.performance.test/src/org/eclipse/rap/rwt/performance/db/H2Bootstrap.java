package org.eclipse.rap.rwt.performance.db;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Bootstrap {

  private static final String SCHEMA = "resources/schema.sql";

  public static void main( String[] args ) {
    try {
      Class.forName( "org.h2.Driver" );
      createConnection();
    } catch( SQLException e ) {
      e.printStackTrace();
    } catch( ClassNotFoundException e ) {
      e.printStackTrace();
    }
  }

  private static void createConnection() throws SQLException {
    Connection con = DriverManager.getConnection( "jdbc:h2:tcp://localhost/~/performance",
                                                  "sa",
                                                  "" );
    Statement stmt = con.createStatement();
    String schema = readSchema();
    stmt.execute( schema );
  }

  private static String readSchema() {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    try {
      ClassLoader loader = H2Bootstrap.class.getClassLoader();
      InputStream stream = loader.getResourceAsStream( SCHEMA );
      BufferedInputStream bis = new BufferedInputStream( stream );
      int result;
      result = bis.read();
      while( result != -1 ) {
        buf.write( ( byte )result );
        result = bis.read();
      }
    } catch( IOException e ) {
      e.printStackTrace();
    }
    return buf.toString();
  }
}
