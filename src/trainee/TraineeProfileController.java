package trainee;

import trainee.trainee;

import java.sql.*;

public class TraineeProfileController {
    private static Connection conn;
    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String username = "root";
    String password = "Example@2021!";
    public TraineeProfileController() throws SQLException {
        conn = DriverManager.getConnection(url, username, password);
    }

    public void setTraineeInfo(trainee mytrainee, String username, String password, String email) {
        mytrainee.setname(username);
        mytrainee.setmail(email);
        mytrainee.setPassword(password);
    }
    public void editprofile(String newName, String newPassword, String email) throws SQLException {
        // Get the current user's ID
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            // Update the user's information in the database
            String sqlupdatetrainee = "UPDATE trainees SET username=?, password=? WHERE email=?";
            PreparedStatement updatetrainee = conn.prepareStatement(sqlupdatetrainee);
            updatetrainee.setString(1, newName);
            updatetrainee.setString(2, newPassword);
            updatetrainee.setString(3, email);
            updatetrainee.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

    public void viewprofile(String email) throws SQLException {
        String sqlviewprofile = "SELECT * FROM trainees WHERE email=?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pviewprofile = conn.prepareStatement(sqlviewprofile)) {
            pviewprofile.setString(1, email);
            ResultSet output = pviewprofile.executeQuery();
            if (output.next()) {
                String name = output.getString("username");
                String password = output.getString("password");
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Password: " + password);
            } else {
                System.out.println("No user found with email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
