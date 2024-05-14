package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "localhost:3306/gestionale_swe";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String mySqlString = "jdbc:mysql://";
            connection = DriverManager.getConnection(mySqlString + URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            try {
            	Class.forName("org.mariadb.jdbc.Driver");
            	String mariaDBString = "jdbc:mariadb://";
            	connection = DriverManager.getConnection(mariaDBString + URL, USER, PASSWORD);
            } catch (ClassNotFoundException | SQLException e2) {
            	throw new RuntimeException(e2);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
