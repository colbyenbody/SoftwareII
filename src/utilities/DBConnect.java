package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles connect/disconnecting from database
 */
public class DBConnect {

    private static Connection conn;
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password

    /**
     * connects to database
     * @throws SQLException if there is an sql error
     * @return connection
     */
    public static Connection connect() throws SQLException{
        try{
            Class.forName(driver);
        }catch(ClassNotFoundException Exception){
            System.err.println("Error: "+Exception.getMessage());
        }

        conn = DriverManager.getConnection(jdbcUrl,userName,password);
        System.out.println("Connection started!");
        return conn;

    }

    /**
     * closeConnection is used to close the open connection with the SQL database
     */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * checks connection status
     * @throws SQLException if there is an sql error
     * @return connection status
     */
    public static Connection getConnection() throws SQLException {
        if(conn !=null && !conn.isClosed())
            return conn;
        connect();
        return conn;

    }
}