package registration;
import trainee.*;
import java.sql.*;

public class RegistrationController {
    private static Connection conn;
    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String username = "root";
    String password = "Example@2021!";
    public RegistrationController() throws SQLException {
        conn = DriverManager.getConnection(url, username, password);
    }

    public void setTraineeInfo(trainee mytrainee, String username, String password, String email) {
        mytrainee.setname(username);
        mytrainee.setmail(email);
        mytrainee.setPassword(password);
    }
    public void signUp(trainee user) throws SQLException {
        String sqlcheckuser = "SELECT * FROM trainees WHERE email=?";
        PreparedStatement checkUser = conn.prepareStatement(sqlcheckuser);
        checkUser.setString(1, user.getmail());
        ResultSet checkUserResult = checkUser.executeQuery();

        if (checkUserResult.next()) {
            System.out.println("USER ALREADY EXISTS! ");
        } else {
            // Add the user to the database
            String sqladduser = "INSERT INTO trainees (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement addUser = conn.prepareStatement(sqladduser);
            addUser.setString(1, user.getname());
            addUser.setString(2, user.getPassword());
            addUser.setString(3, user.getmail());
            addUser.executeUpdate();
            System.out.println("Welcome, " + user.getname() + "! You are now part of our system ;-) ");
            System.out.println("--------------------------------------------");
        }
    }


    public static boolean login(String email, String password) throws SQLException {
        // Check if the username and password are correct
        String sqlchecklogin = "SELECT * FROM trainees WHERE email=? AND password=?";
        PreparedStatement checkLogin = conn.prepareStatement(sqlchecklogin);
        checkLogin.setString(1, email);
        checkLogin.setString(2, password);
        ResultSet checkLoginoutput = checkLogin.executeQuery();

        String name;
        if (checkLoginoutput.next()) {
            name = checkLoginoutput.getString("username");
            System.out.println("Login Successful! Welcome, " + name);
            return true;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return false;
        }
    }
}
