package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static Connection connection;
    private static String url = "jdbc:mysql://localhost:3306/";
    private static String timezoneTag = "?serverTimezone=UTC";

    public static void createConnection(String username, String password, String dbName) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        connection = DriverManager.getConnection(url + dbName + timezoneTag, props);
    }

    public static Connection getConnection(){
        return connection;
    }
}
