package localhost.mysqlsandbox.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import localhost.mysqlsandbox.util.MysqlHelper;
import localhost.mysqlsandbox.util.MysqlHelper.StatementMethod;

public class Test00 {

	public static void main() {
		System.out.println("Test00 -- Start main()");
		test00();
	}

	private static void test00() {
		System.out.println("Test00 -- Start test00()");

		// connection params
		String host = "localhost";
		int port = 3306;
		String db = "00_test_large_table";
		String user = "root";
		String password = "1234";

		// sql
		String sql = "select 1 as 'id', 'hello world' as 'text' "
				+ "union select 2, 'hi again world' "
				+ "union select 3, 'hi yet again, beautiful world' "
				+ ";";

		// execute sql query
		Connection conn = null;
		Statement stmt = null;
		StatementMethod stmtMethod = null;
		ResultSet rs = null;
		try {
			conn = MysqlHelper.getConnection(host, port, db, user, password);
			stmt = conn.createStatement();
			stmtMethod = StatementMethod.EXECUTE_QUERY;
			rs = MysqlHelper.execute(conn, stmt, stmtMethod,  sql);
		} catch (Throwable ex) {
			System.err.println("exception -- " + ex);
			return;
		}

		// process ResultSet
		System.out.println("rs: " + rs);
		Integer id = null;
		String text = null;
		try {
			while(rs.next()) {
				id = rs.getInt("id");
				text = rs.getString("text");
				System.out.println("rs -- id: " + id + ", text: " + text);
			}
		} catch (Throwable ex) {
			System.err.println("exception -- " + ex);
		}

		// close Statement, ResultSet
		MysqlHelper.closeStmtAndRS(stmt, rs);

		// close Connection
		MysqlHelper.closeConn(conn);

		System.out.println("done!");
	}

}
