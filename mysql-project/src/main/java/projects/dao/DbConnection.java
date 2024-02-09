package projects.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import projects.exception.*;

public class DbConnection {
	
	//Establishing values for DB access
	private static final String SCHEMA = "project";
	private static final String USER = "project";
	private static final String PASSWORD = "project";
	private static final String HOST = "localhost";
	private static final int PORT = 3306;
	
	//creating the method to connect to the DB
	public static Connection getConnection() {
		String url = 
				String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s&allowPublicKeyRetrieval=true&useSSL=false", 
						HOST, PORT, SCHEMA, USER, PASSWORD);
		
		System.out.println("Connecting with url = " + url);
		
		//DB connection with catch block to throw an exception
		try {
			Connection conn = DriverManager.getConnection(url);
			System.out.println("Successfully obtained connection!");
			return conn;
		} catch (SQLException e) {
			throw new DbException(e);
		}
		
	}
	
}

