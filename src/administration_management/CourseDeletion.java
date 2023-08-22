package administration_management;

import java.sql.*;
import java.util.Scanner;

public class CourseDeletion implements ICourseManagement {

    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String user = "root";
    String password = "Example@2021!";
String cname;
    public CourseDeletion(String cname)
    {
        this.cname=cname;
    }

    @Override
    public void managecourse() {


        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sqldeletecourse = "DELETE FROM courses WHERE course_name=?";
            PreparedStatement deletecourse = conn.prepareStatement(sqldeletecourse);
            deletecourse.setString(1, cname);
            int rows = deletecourse.executeUpdate();
            if (rows > 0) {
                System.out.println("Course deleted successfully!");
            } else {
                System.out.println("Please check if the course name is correct.");
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}