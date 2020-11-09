package jdbcdemo;

import com.mysql.cj.xdevapi.PreparableStatement;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static String DB_URL = "jdbc:mysql://localhost:3306/diablo?serverTimezone=UTC";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "root");

        try (Connection connection = DriverManager.getConnection(DB_URL, props)){
            String username = sc.nextLine();
            PreparedStatement ps = connection.prepareStatement("SELECT u.first_name, u.last_name, COUNT(*) AS count\n" +
                    "FROM users AS u\n" +
                    "JOIN users_games AS ug\n" +
                    "ON u.id = ug.user_id\n" +
                    "WHERE u.user_name = ?;");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            int gamesPlayed = rs.getInt("count");
            if(gamesPlayed <= 0)
            {
                System.out.println("No such user exists");
            }
            else{
                System.out.printf("User: %s%n%s %s has played %d games",
                        username,
                        firstName,
                        lastName,
                        gamesPlayed);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
