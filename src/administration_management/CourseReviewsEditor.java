package administration_management;

import java.sql.*;
import java.util.Scanner;

public class CourseReviewsEditor implements ICourseManagement {

    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String user = "root";
    String password = "Example@2021!";

    String cname;
    String creview;
    public CourseReviewsEditor(String cname, String creview)
    {
        this.cname=cname;
        this.creview=creview;
    }

    @Override
    public void managecourse() {

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sqlupdatereview = "UPDATE courses SET course_reviews=? WHERE course_name=?";
            PreparedStatement updatereview = conn.prepareStatement(sqlupdatereview);
            updatereview.setString(1, creview);
            updatereview.setString(2, cname);
            updatereview.executeUpdate();
            System.out.println("course review updated successfully");
            System.out.println("--------------------------------------------");

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }
}
