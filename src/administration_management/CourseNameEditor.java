package administration_management;

import java.sql.*;
import java.util.Scanner;

public class CourseNameEditor implements ICourseManagement {

    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String user = "root";
    String password = "Example@2021!";
    String cname;
    String cname2;
    public CourseNameEditor(String cname, String cname2)
    {
        this.cname=cname;
        this.cname2=cname2;
    }
    @Override
    public void managecourse() {

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sqlupdatename = "UPDATE courses SET course_name=? WHERE course_name=?";
            PreparedStatement updatename = conn.prepareStatement(sqlupdatename);
            updatename.setString(1, cname2);
            updatename.setString(2, cname);
            updatename.executeUpdate();
            System.out.println("course name updated successfully");
            System.out.println("--------------------------------------------");

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }
}
