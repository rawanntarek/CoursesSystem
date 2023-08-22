package registration;

import administration_management.administrationstaff;

import java.sql.*;
public class AdminLoginController {
    private static Connection conn;
    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String username = "root";
    String password = "Example@2021!";

    public AdminLoginController() throws SQLException {
        conn = DriverManager.getConnection(url, username, password);
    }

    public void setadministrator(administrationstaff administrator, String username, String password, String email) {
        administrator.setname(username);
        administrator.setmail(email);
        administrator.setPassword(password);
    }

    public static boolean login(String email, String password) throws SQLException {
        // Check if the username and password are correct
        String name;
        String sqlcheckLogin = "SELECT * FROM adminstrationstaff WHERE email=? AND password=?";
        PreparedStatement checkloginStatement = conn.prepareStatement(sqlcheckLogin);
        checkloginStatement.setString(1, email);
        checkloginStatement.setString(2, password);
        ResultSet checkLoginResult = checkloginStatement.executeQuery();

        if (checkLoginResult.next()) {
            name = checkLoginResult.getString("name");
            System.out.println("Login Successful! Welcome, " + name);
            return true;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return false;
        }
    }
}
