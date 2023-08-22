package administration_management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class CourseMaterialEditor implements ICourseManagement {

    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String user = "root";
    String password = "Example@2021!";
     //String courseName, File course_material,String course_material_name,
    String cname;
    File cmaterial;
    String cmaterialname;
    public CourseMaterialEditor(String cname, File cmaterial, String cmaterialname)
    {
        this.cname=cname;
        this.cmaterial=cmaterial;
        this.cmaterialname=cmaterialname;

    }
    @Override
    public void managecourse() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sqlupdatematerial = "UPDATE courses SET course_material=? , course_material_name=? WHERE course_name=?";
            FileInputStream inputStream = new FileInputStream(cmaterial);
            PreparedStatement updateMaterial = conn.prepareStatement(sqlupdatematerial);
            updateMaterial.setBinaryStream(1, inputStream, cmaterial.length());
            updateMaterial.setString(2, cmaterialname);
            updateMaterial.setString(3, cname);
            updateMaterial.executeUpdate();
            System.out.println("course material updated successfully");
            System.out.println("--------------------------------------------");

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
