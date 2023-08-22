package trainee_management;

import trainee.trainee;

import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CourseMaterial_QuizzesController {
    private static Connection conn;
    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String username = "root";
    String password = "Example@2021!";
    public CourseMaterial_QuizzesController() throws SQLException {
        conn = DriverManager.getConnection(url, username, password);
    }

    public void setTraineeInfo(trainee mytrainee, String username, String password, String email) {
        mytrainee.setname(username);
        mytrainee.setmail(email);
        mytrainee.setPassword(password);
    }
    public void viewQuizzes(String coursename) {
        try {
            String query = "SELECT quiz_name FROM quizzes WHERE coursename IN (SELECT courses_enrolled FROM enrollments WHERE courses_enrolled=?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, coursename);
            ResultSet rs = statement.executeQuery();

            Set<String> quizNames = new HashSet<>();
            while (rs.next()) {
                String quizname = rs.getString("quiz_name");
                if (!quizNames.contains(quizname)) {
                    quizNames.add(quizname);
                    System.out.println(quizname);
                }
            }
            if (quizNames.isEmpty()) {
                System.out.println("No quizzes found for that course.");
            }
            System.out.println("--------------------------------------------");
        } catch (SQLException e) {
            System.out.println("Error retrieving quizzes: " + e.getMessage());
            System.out.println("--------------------------------------------");
        }
    }
    public void viewExams(String coursename) {
        try {
            String query = "SELECT examname FROM exams WHERE coursename IN (SELECT courses_enrolled FROM enrollments WHERE courses_enrolled=?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, coursename);
            ResultSet rs = statement.executeQuery();

            Set<String> quizNames = new HashSet<>();
            while (rs.next()) {
                String examname = rs.getString("examname");
                if (!quizNames.contains(examname)) {
                    quizNames.add(examname);
                    System.out.println(examname);
                }
            }
            if (quizNames.isEmpty()) {
                System.out.println("No exams found for that course.");
            }
            System.out.println("--------------------------------------------");
        } catch (SQLException e) {
            System.out.println("Error retrieving exams: " + e.getMessage());
            System.out.println("--------------------------------------------");
        }
    }
    public void solveexams(String coursename, String examname, String userEmail) {
        try {
            String sqlchecksolved = "SELECT * FROM finalexam WHERE email = ? AND examname = ? AND coursename = ?";
            PreparedStatement checksolved = conn.prepareStatement(sqlchecksolved);
            checksolved.setString(1, userEmail);
            checksolved.setString(2, examname);
            checksolved.setString(3, coursename);
            ResultSet solvedresult = checksolved.executeQuery();
            if (solvedresult.next()) {
                System.out.println("You have already solved this quiz!");
                System.out.println("--------------------------------------------");
                return;
            }
            // User is enrolled, so proceed to retrieve quiz
            String sqlretrievequiz = "SELECT * FROM exams WHERE coursename = ? AND examname = ?";
            PreparedStatement retrievequiz = conn.prepareStatement(sqlretrievequiz);
            retrievequiz.setString(1, coursename);
            retrievequiz.setString(2, examname);
            ResultSet output = retrievequiz.executeQuery();

            if (output.next()) {
                int score = 0;
                int total_questions = 0;
                do {
                    String question = output.getString("question");
                    String choice_1 = output.getString("choice1");
                    String choice_2 = output.getString("choice2");
                    String correct_answer = output.getString("answer");

                    System.out.println(question);
                    System.out.println("1. " + choice_1);
                    System.out.println("2. " + choice_2);

                    Scanner input = new Scanner(System.in);
                    System.out.print("Enter your answer (1-2): ");
                    int user_answer = input.nextInt();

                    if (user_answer == Integer.parseInt(correct_answer)) {
                        System.out.println("Correct!");
                        score++;
                    } else {
                        System.out.println("Incorrect!");
                    }

                    total_questions++;
                } while (output.next());

                double percentage = (score / (double) total_questions) * 100;

                System.out.println("--------------------------------------------");
                System.out.println("Your score for the final exam is: " + score);
                System.out.println("Your percentage for the final exam is: " + percentage);

                // Insert score, quizname, coursename into progress table
                String insertQuery = "INSERT INTO finalexam (email, coursename, percentage, examname) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
                insertStatement.setString(1, userEmail);
                insertStatement.setString(2, coursename);
                insertStatement.setDouble(3, percentage);
                insertStatement.setString(4, examname);
                insertStatement.executeUpdate();

                System.out.println("--------------------------------------------");

            } else {
                System.out.println("not found");
                System.out.println("--------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving exam maybe you are not enrolled: " + e.getMessage());
            System.out.println("--------------------------------------------");
        }
    }




    public void solveQuizzes(String coursename, String quizname,String userEmail) {
        try {

            String sqlchecksolved = "SELECT * FROM trainee_quizzes WHERE email = ? AND quiz_name = ? AND coursename = ?";
            PreparedStatement checksolved = conn.prepareStatement(sqlchecksolved);
            checksolved.setString(1, userEmail);
            checksolved.setString(2, quizname);
            checksolved.setString(3, coursename);
            ResultSet solvedresult = checksolved.executeQuery();
            if (solvedresult.next()) {
                System.out.println("You have already solved this quiz!");
                System.out.println("--------------------------------------------");
                return;
            }
            // User is enrolled, so proceed to retrieve quiz
            String sqlretrievequiz = "SELECT * FROM quizzes WHERE coursename = ? AND quiz_name = ?";
            PreparedStatement retrievequiz = conn.prepareStatement(sqlretrievequiz);
            retrievequiz.setString(1, coursename);
            retrievequiz.setString(2, quizname);
            ResultSet output = retrievequiz.executeQuery();

            if (output.next()) {
                int score = 0;
                do {
                    String question = output.getString("questions");
                    String choice_1 = output.getString("choice1");
                    String choice_2 = output.getString("choice2");
                    String correct_answer = output.getString("answer");

                    System.out.println(question);
                    System.out.println("1. " + choice_1);
                    System.out.println("2. " + choice_2);

                    Scanner input = new Scanner(System.in);
                    System.out.print("Enter your answer (1-2): ");
                    int user_answer = input.nextInt();

                    if (user_answer == Integer.parseInt(correct_answer)) {
                        System.out.println("Correct!");
                        score++;
                    } else {
                        System.out.println("Incorrect!");
                    }
                } while (output.next());

                System.out.println("--------------------------------------------");
                System.out.println("Your score for the quiz is: " + score );

                // Insert score, quizname, coursename into progress table
                String insertQuery = "INSERT INTO trainee_quizzes (email, score, quiz_name, coursename) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
                insertStatement.setString(1, userEmail);
                insertStatement.setInt(2, score);
                insertStatement.setString(3, quizname);
                insertStatement.setString(4, coursename);
                insertStatement.executeUpdate();

                System.out.println("--------------------------------------------");
                System.out.println("Score has been added to the trainees quizzes table.");
                System.out.println("--------------------------------------------");
            } else {
                System.out.println("not found");
                System.out.println("--------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving quiz maybe you are not enrolled: " + e.getMessage());
            System.out.println("--------------------------------------------");
        }
    }


    public void viewcoursematerial(String coursename) throws SQLException {
        String sqlviewmaterial = "SELECT course_material_name FROM courses WHERE course_name=?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement viewmaterial = conn.prepareStatement(sqlviewmaterial)) {
            viewmaterial.setString(1, coursename);

            ResultSet output = viewmaterial.executeQuery();
            if (output.next()) {
                String name = output.getString("course_material_name");
                System.out.println("course material: " + name);
            } else {
                System.out.println("No course material found with course name: " + coursename);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void viewprogress(String email,String coursename) {
        try {
            String sqlviewprogress = "SELECT * FROM progress WHERE user_email = ? AND coursename=?";
            PreparedStatement viewprogress = conn.prepareStatement(sqlviewprogress);
            viewprogress.setString(1, email);
            viewprogress.setString(2, coursename);
            ResultSet output = viewprogress.executeQuery();

            if (output.next()) {

                do {
                    System.out.println("your progress in :" + coursename);
                    double percent = output.getDouble("percentage");
                    double finalpercent = output.getDouble("finalexam_percent");
                    System.out.println("course percentage:"+percent+"%");
                    System.out.println("final exam percentage:"+finalpercent+"%");
                    System.out.println("--------------------------------------------");
                } while (output.next());
            } else {
                System.out.println(" you currently have no progress.");
                System.out.println("--------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving progress: " + e.getMessage());
            System.out.println("--------------------------------------------");
        }
    }
}
