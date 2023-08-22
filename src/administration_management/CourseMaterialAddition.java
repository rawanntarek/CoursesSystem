package administration_management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseMaterialAddition implements ICourseAddition {
    private static Connection conn;
    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String username = "root";
    String password = "Example@2021!";
    String courseName;
    File course_material;
    String course_material_name;
    String course_prerequesites;
    public CourseMaterialAddition(String courseName, File course_material, String course_material_name, String course_prerequesites)
    {
        this.courseName=courseName;
        this.course_material=course_material;
        this.course_material_name=course_material_name;
        this.course_prerequesites=course_prerequesites;
    }
    @Override
    public void adding() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            FileInputStream input  = new FileInputStream(course_material);
            String sqlupdateMaterial = "UPDATE courses SET course_material=?,course_material_name=? ,course_prerequesites=? WHERE course_name=?";
            PreparedStatement updatematerial = conn.prepareStatement(sqlupdateMaterial);
            updatematerial.setString(2, course_material_name);
            updatematerial.setString(3, course_prerequesites);
            updatematerial.setString(4, courseName);
            updatematerial.setBinaryStream(1, input, course_material.length());
            int rows = updatematerial.executeUpdate();
            if (rows > 0) {
                System.out.println("Material updated successfully!");
                System.out.println("--------------------------------------------");
            } else {
                System.out.println("Please check if the course name is correct.");
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
