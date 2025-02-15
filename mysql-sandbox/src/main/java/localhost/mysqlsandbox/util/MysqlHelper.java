package localhost.mysqlsandbox.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MysqlHelper {

	private static Connection getConnectionDefault() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/00_test_large_table?" +
					"user=root&password=1234");
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}


	public static Connection getConnection(String host, int port, String db, String user, String password) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db + "?" +
					"user=" + user + "&password=" + password);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}


	public static ResultSet execute(Connection conn, Statement stmt, StatementMethod stmtMethod, String sql) {
		ResultSet rs = null;

		try {
			switch (stmtMethod) {
			case EXECUTE_QUERY:
				rs = stmt.executeQuery(sql); // typically SELECT
				break;
			case EXECUTE:
				if (stmt.execute(sql)) { // multiple types of results
					rs = stmt.getResultSet();
				}
				break; 
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return rs;
	}


	/**
	 * <p>Always good to close unused Statements and ResultSets. 
	 */
	public static void closeStmtAndRS(Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException sqlEx) { 
				// ignore
			} 
			rs = null;
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException sqlEx) { 
				// ignore
			} 
			stmt = null;
		}
	}


	/**
	 * <p>Always good to close Connection(s) when finalizing. 
	 */
	public static void closeConn(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException sqlEx) { 
				// ignore
			} 
			conn = null;
		}
	}


	public static enum StatementMethod {

		/**
		 * <p>Typically for SELECT sql queries.
		 */
		EXECUTE_QUERY,

		/**
		 * <p>For multiple result types (counts, rows, etc).
		 */
		EXECUTE
	}
}
