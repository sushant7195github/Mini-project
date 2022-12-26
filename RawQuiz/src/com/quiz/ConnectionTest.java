package com.quiz;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionTest {
	Connection connection = null;// create global variable of connection

	public Connection getConnectionDetails() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "2525");
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}
}
