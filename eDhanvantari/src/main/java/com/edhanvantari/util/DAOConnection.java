package com.edhanvantari.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAOConnection {

	/**
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection connection = null;

		ConfigXMLUtil configXMLUtil = new ConfigXMLUtil();

		// Fetching Hostname from XML
		String DBHostName = configXMLUtil.getDBIP().trim();

		// Fetching DB Port No
		String DBPort = configXMLUtil.getDBPort().trim();

		// Fetching DB name from XML
		String DBName = configXMLUtil.getDBName().trim();

		String DBDriver = "com.mysql.cj.jdbc.Driver";
		/*
		 * String DBUrl = "jdbc:mysql://" + DBHostName + ":" + DBPort + "/" + DBName +
		 * "?allowPublicKeyRetrieval=true&characterEncoding=UTF-8&useSSL=false";
		 */
		String DBUrl = "jdbc:mysql://" + DBHostName + ":" + DBPort + "/" + DBName
				+ "?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=IST&allowPublicKeyRetrieval=true";

		// Fetching DB Username from XML
		String DBUser = configXMLUtil.getDBUsername().trim();

		// Fetching DB Password from XML
		String DBPass = configXMLUtil.getDBPassword().trim();

		try {
			Class.forName(DBDriver);

			connection = DriverManager.getConnection(DBUrl, DBUser, DBPass);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Failed to establish connection due to ::::::" + exception.getMessage());
		}
		return connection;

	}

	/**
	 * 
	 * @param realPath
	 * @return
	 */
	public static Connection getConnection(String realPath) {
		Connection connection = null;

		ConfigListenerUtil configXMLUtil = new ConfigListenerUtil();

		// Fetching Hostname from XML
		String DBHostName = configXMLUtil.getDBIP(realPath).trim();

		// Fetching DB Port No
		String DBPort = configXMLUtil.getDBPort(realPath).trim();

		// Fetching DB name from XML
		String DBName = configXMLUtil.getDBName(realPath).trim();

		String DBDriver = "com.mysql.cj.jdbc.Driver";
		/*
		 * String DBUrl = "jdbc:mysql://" + DBHostName + ":" + DBPort + "/" + DBName +
		 * "?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
		 */
		String DBUrl = "jdbc:mysql://" + DBHostName + ":" + DBPort + "/" + DBName
				+ "?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=IST&allowPublicKeyRetrieval=true";

		// Fetching DB Username from XML
		String DBUser = configXMLUtil.getDBUsername(realPath).trim();

		// Fetching DB Password from XML
		String DBPass = configXMLUtil.getDBPassword(realPath).trim();

		try {
			Class.forName(DBDriver);

			connection = DriverManager.getConnection(DBUrl, DBUser, DBPass);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Failed to establish connection duee to ::::::" + exception.getMessage());
		}
		return connection;

	}
}
