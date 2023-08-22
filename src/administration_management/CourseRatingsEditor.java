package administration_management;

import java.sql.*;
import java.util.Scanner;

public class CourseRatingsEditor implements ICourseManagement {

    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String user = "root";
    String password = "Example@2021!";
    String cname;
    int crating;
    public CourseRatingsEditor(String cname, int crating)
    {
        this.cname=cname;
        this.crating=crating;
    }
    @Override
    public void managecourse() {


        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sqlupdateratings = "UPDATE courses SET course_rating=? WHERE course_name=?";
            PreparedStatement updateratings = conn.prepareStatement(sqlupdateratings);
            updateratings.setInt(1, crating);
            updateratings.setString(2, cname);
            updateratings.executeUpdate();
            System.out.println("ratings updated successfully");
            System.out.println("--------------------------------------------");

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }
}
