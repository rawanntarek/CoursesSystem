package administration_management;

import java.sql.*;
import java.util.Scanner;

public class CoursePrerequesitesEditor implements ICourseManagement {

    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String user = "root";
    String password = "Example@2021!";
    String cname;
    String cprerequesites;
    public CoursePrerequesitesEditor(String cname, String cprerequesites)
    {
        this.cname=cname;
        this.cprerequesites=cprerequesites;
    }
    @Override
    public void managecourse() {


        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sqlupdateprerequesites = "UPDATE courses SET course_prerequesites=? WHERE course_name=?";
            PreparedStatement updateprerequesites= conn.prepareStatement(sqlupdateprerequesites);
            updateprerequesites.setString(1, cprerequesites);
            updateprerequesites.setString(2, cname);
            updateprerequesites.executeUpdate();
            System.out.println("course prerequesites updated successfully");
            System.out.println("--------------------------------------------");

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }
}
