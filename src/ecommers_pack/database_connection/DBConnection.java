package ecommers_pack.database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce_db";
	    private static final String USER = "root";
	    private static final String PASSWORD = "pinky@74";

	    public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }
	}

	

