package SearchingTechniques;

import SearchingTechniques.Isearchingmethod;

import java.sql.*;

public class ReviewsSearcher implements Isearchingmethod {
    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String user = "root";
    String password = "Example@2021!";
    String reviews;
    public ReviewsSearcher(String reviews)
    {
        this.reviews=reviews;
    }
    @Override
    public void search() {


        String sqlsearchbyreviews = "SELECT * FROM courses WHERE course_reviews=?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement psearchbyreviews = conn.prepareStatement(sqlsearchbyreviews)) {
            psearchbyreviews.setString(1,reviews);
            ResultSet output = psearchbyreviews.executeQuery();
            boolean coursefound=false;
            int i=0;
            while (output.next()) {
                String name = output.getString("course_name");
                int ratings = output.getInt("course_rating");
                System.out.println("course " + (i+1) +":");
                System.out.println("course name: " + name);
                System.out.println("course rating: " + ratings);
                System.out.println("course review: " + reviews);
                System.out.println("--------------------------");
                i++;
                coursefound=true;
            } if(!coursefound) {
                System.out.println("No course found with review: " + reviews);
                System.out.println("--------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
