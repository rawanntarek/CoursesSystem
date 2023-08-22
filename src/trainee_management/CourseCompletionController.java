package trainee_management;
import trainee.trainee;
import java.sql.*;
import java.util.Scanner;
import course.*;
public class CourseCompletionController {
    private static Connection conn;
    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String username = "root";
    String password = "Example@2021!";

    public CourseCompletionController() throws SQLException {
        conn = DriverManager.getConnection(url, username, password);
    }

    public void setTraineeInfo(trainee mytrainee, String username, String password, String email) {
        mytrainee.setname(username);
        mytrainee.setmail(email);
        mytrainee.setPassword(password);
    }



    public void requestCertificate(String courseName, String userEmail) {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {

                // Get the certificate fee from the courses table
                String sql = "SELECT certificate_fee FROM courses WHERE course_name=?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, courseName);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    int certificateFee = rs.getInt("certificate_fee");
                    System.out.println("The certificate fee for " + courseName + " is " + certificateFee);
                    int option;
                    Scanner input = new Scanner(System.in);
                    System.out.println("choose an option:");
                    System.out.println("1-pay by cash");
                    System.out.println("2-pay by credit card");
                    option = input.nextInt();
                    switch (option) {
                        case 1:
                            System.out.println("Visit the nearest branch:");
                            System.out.println("1- 32 Dokki Mossadak");
                            System.out.println("2- 1135 Zahraa Nasr City");
                            System.out.println("3- 9th District Zayed City");
                            break;
                        case 2:
                            System.out.print("Enter a credit card number(a real one): ");
                            String input1;
                            input1 = input.nextLine();

                            // Remove any whitespace from the input string
                            String creditCardNumber = input.nextLine();
// Check if the credit card number is valid and determine the card type
                            String cardType;
                            if (creditCardNumber.matches("^4[0-9]{12}(?:[0-9]{3})?$")) {
                                cardType = "Visa";
                            } else {
                                String prefix = creditCardNumber.substring(0, 2);
                                switch (prefix) {
                                    case "37":
                                    case "34":
                                        cardType = "American Express";
                                        break;
                                    case "51":
                                    case "52":
                                    case "53":
                                    case "54":
                                    case "55":
                                        cardType = "Mastercard";
                                        break;
                                    default:
                                        cardType = "Unknown";
                                        break;
                                }
                            }

                            // Check if the credit card number is valid using the Luhn algorithm.
                            boolean isValid = false;
                            String reversedNumber = new StringBuilder(creditCardNumber).reverse().toString();
                            int sum = 0;
                            for (int i = 0; i < reversedNumber.length(); i++) {
                                int digit = Character.digit(reversedNumber.charAt(i), 10);
                                if (i % 2 == 1) {
                                    digit *= 2;
                                    if (digit > 9) {
                                        digit -= 9;
                                    }
                                }
                                sum += digit;
                            }
                            if (sum % 10 == 0) {
                                isValid = true;
                            }

                            if (isValid) {
                                System.out.println("The credit card number is valid and the purchaces is completed and it is a " + cardType + " card.");
                                String sqlcertificate = "UPDATE progress SET certificate_status='issued' WHERE coursename=? AND user_email=?";
                                PreparedStatement certificate = conn.prepareStatement(sqlcertificate);
                                certificate.setString(1, courseName);
                                certificate.setString(2, userEmail);
                                certificate.executeUpdate();


                            } else {
                                System.out.println("The credit card number is invalid.");
                            }
                            break;
                        default:
                            System.out.println("Invalid option.");
                            break;
                    }
                } else {
                    System.out.println("Could not find the course " + courseName);
                }

            } catch(SQLException e){
                System.out.println("Error requesting certificate: " + e.getMessage());
            }

    }

    public boolean isCourseCompleted(String courseName, String userEmail) throws SQLException {
        String sql = "SELECT * FROM progress WHERE coursename=? AND user_email=? AND percentage>=50 AND finalexam_percent>=50";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, courseName);
        statement.setString(2, userEmail);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }



    public void withdraw_fromcourse(courses c, String mail, String enrolled) {

        c.setname(enrolled);
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sqlwithdraw = "DELETE FROM enrollments WHERE courses_enrolled=? AND user_email=?";
            PreparedStatement withdraw = conn.prepareStatement(sqlwithdraw);
            withdraw.setString(1, enrolled);
            withdraw.setString(2, mail);
            int rows = withdraw.executeUpdate();
            if (rows > 0) {
                System.out.println("withdrawal done successfully!");
                System.out.println("--------------------------------------------");
            } else {
                System.out.println("Please check if the course name is correct.");
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}