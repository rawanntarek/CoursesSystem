package SearchingTechniques;

import SearchingTechniques.Isearchingmethod;

import java.sql.*;

public class NameSearcher implements Isearchingmethod {
    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String user = "root";
    String password = "Example@2021!";

    String name;
    public NameSearcher(String name)
    {
        this.name=name;
    }
    @Override
    public void search() {
        String sqlsearchbyname = "SELECT * FROM courses WHERE course_name=?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement psearchbyname = conn.prepareStatement(sqlsearchbyname)) {
            psearchbyname.setString(1,name);
            ResultSet output = psearchbyname.executeQuery();
            boolean coursefound=false;
            int i=0;
            while (output.next()) {
                int rate = output.getInt("course_rating");
                String reviews = output.getString("course_reviews");
                System.out.println("course " + (i+1) +":");
                System.out.println("course name: " + name);
                System.out.println("course rating: " + rate);
                System.out.println("course review: " + reviews);
                System.out.println("--------------------------");
                i++;
                coursefound=true;
            } if(!coursefound) {
                System.out.println("No course found with name: " + name);
                System.out.println("--------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

