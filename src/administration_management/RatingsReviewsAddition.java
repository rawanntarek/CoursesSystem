package administration_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RatingsReviewsAddition implements ICourseAddition {

    private static Connection conn;
    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String username = "root";
    String password = "Example@2021!";
    String coursename;
    String newreview;
    int newrating;
    public RatingsReviewsAddition(String coursename, int newrating, String newreview)
    {
        this.coursename=coursename;
        this.newreview=newreview;
        this.newrating=newrating;
    }
    @Override
    public void adding() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            // Update the course's information in the database
            String sqlupdateCourse = "UPDATE courses SET course_rating=?, course_reviews=? WHERE course_name=?";
            PreparedStatement updatecoursestatement = conn.prepareStatement(sqlupdateCourse);
            updatecoursestatement.setInt(1, newrating);
            updatecoursestatement.setString(2, newreview);
            updatecoursestatement.setString(3, coursename);
            int numRowsAffected = updatecoursestatement.executeUpdate();
            if (numRowsAffected > 0) {
                System.out.println("Course information updated successfully!");
                System.out.println("--------------------------------------------");
            } else {
                System.out.println("Please check if the course name is correct.");
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
