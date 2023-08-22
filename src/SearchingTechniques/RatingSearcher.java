package SearchingTechniques;

import SearchingTechniques.Isearchingmethod;

import java.sql.*;

public class RatingSearcher implements Isearchingmethod {
    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String user = "root";
    String password = "Example@2021!";
    int rate;
    public RatingSearcher(int rate)
    {
        this.rate=rate;
    }
    @Override
    public void search() {
        String sqlsearchbyrating = "SELECT * FROM courses WHERE course_rating=?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement psearchbyrating = conn.prepareStatement(sqlsearchbyrating)) {
            psearchbyrating.setInt(1,rate);
            ResultSet output = psearchbyrating.executeQuery();
            boolean course_found=false;
            int i=0;
            while (output.next()) {
                String name = output.getString("course_name");
                String reviews = output.getString("course_reviews");
                System.out.println("course " + (i+1) +":");
                System.out.println("course name: " + name);
                System.out.println("course rating: " + rate);
                System.out.println("course review: " + reviews);
                System.out.println("--------------------------");
                i++;
                course_found=true;
            } if(!course_found) {
                System.out.println("No course found with rating: " + rate);
                System.out.println("--------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
