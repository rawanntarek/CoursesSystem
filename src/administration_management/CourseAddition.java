package administration_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseAddition implements ICourseAddition {
    private static Connection conn;
    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String username = "root";
    String password = "Example@2021!";
    String cname;
    public CourseAddition(String cname)
    {
        this.cname=cname;
    }

    @Override
    public void adding() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, password);
        String sqladdCourse = "INSERT INTO courses (course_name) VALUES (?)";
        PreparedStatement addcoursestatement = conn.prepareStatement(sqladdCourse);
        addcoursestatement.setString(1, cname);
        int rowsAffected = addcoursestatement.executeUpdate();

        if (rowsAffected == 1) {
            System.out.println("Course added successfully!");
            System.out.println("--------------------------------------------");
        } else {
            System.out.println("Error adding course. Please try again.");
        }

    }
}
