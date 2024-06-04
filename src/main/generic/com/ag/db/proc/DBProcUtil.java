package com.ag.db.proc;

import java.sql.Connection;
import java.sql.DriverManager;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;

public class DBProcUtil {

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String dbURL = AppProp.getProperty("db.url");
			String username = AppProp.getProperty("db.username");
			String password = AppProp.getProperty("db.pass");
			AgLogger.logInfo("dbUrl: " + dbURL+"|"+username);
			con = DriverManager.getConnection(dbURL, username, password);
			AgLogger.logInfo("Connection established ...");
		} catch (Exception e) {
			e.printStackTrace();

		}
		return con;
	}

	public static void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
