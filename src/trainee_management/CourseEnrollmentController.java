package trainee_management;

import trainee.trainee;

import java.sql.*;
import course.*;
public class CourseEnrollmentController {
    private static Connection conn;
    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String username = "root";
    String password = "Example@2021!";
    public CourseEnrollmentController() throws SQLException {
        conn = DriverManager.getConnection(url, username, password);
    }

    public void setTraineeInfo(trainee mytrainee, String username, String password, String email) {
        mytrainee.setname(username);
        mytrainee.setmail(email);
        mytrainee.setPassword(password);
    }
    public void course_enrollment(courses c, String mail, String coursename) {
        c.setname(coursename);
        try {
            String sqlcheckcourse = "SELECT COUNT(*) FROM courses WHERE course_name = ?";
            PreparedStatement checkcourse = conn.prepareStatement(sqlcheckcourse);
            checkcourse.setString(1, coursename);
            ResultSet output = checkcourse.executeQuery();
            if (!output.next() || output.getInt(1) == 0) {
                System.out.println("Error: Course not found in the courses database.");
                return;
            }
            String sqlcheckenrollment = "SELECT COUNT(*) FROM enrollments WHERE user_email = ? AND courses_enrolled = ?";
            PreparedStatement checkEnrollment = conn.prepareStatement(sqlcheckenrollment);
            checkEnrollment.setString(1, mail);
            checkEnrollment.setString(2, coursename);
            ResultSet output2 = checkEnrollment.executeQuery();
            if (output2.next() && output2.getInt(1) > 0) {
                System.out.println("Error: Trainee is already enrolled in this course.");
                return;
            }
            String sqladdenrollment = "INSERT INTO enrollments ( user_email,courses_enrolled) VALUES ((SELECT email FROM trainees WHERE email=?),(SELECT course_name FROM courses WHERE course_name=?))";
            PreparedStatement addenrollment = conn.prepareStatement(sqladdenrollment);
            addenrollment.setString(1, mail);
            addenrollment.setString(2, coursename);


            addenrollment.executeUpdate();
            System.out.println("Enrollment successful!");
            System.out.println("--------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error inserting enrollment record: " + e.getMessage());
            System.out.println("--------------------------------------------");
        }
    }
    public void viewEnrolledCourses(String email) {
        try {
            String sqlviewenrolled = "SELECT courses_enrolled FROM enrollments WHERE user_email = ?";
            PreparedStatement viewenrolled = conn.prepareStatement(sqlviewenrolled);
            viewenrolled.setString(1, email);
            ResultSet output = viewenrolled.executeQuery();

            if (output.next()) {
                System.out.println("Courses you are enrolled in :");
                do {
                    String courseName = output.getString("courses_enrolled");
                    System.out.println(courseName);
                } while (output.next());
                System.out.println("--------------------------------------------");
            } else {
                System.out.println(" you are not currently enrolled in any courses.");
                System.out.println("--------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving enrolled courses: " + e.getMessage());
            System.out.println("--------------------------------------------");
        }
    }
    public boolean isEnrolled(String coursename, String userEmail) {
        try {
            String sqlcheckenrollment = "SELECT * FROM enrollments WHERE user_email = ? AND courses_enrolled = ?";
            PreparedStatement checkenrollment = conn.prepareStatement(sqlcheckenrollment);
            checkenrollment.setString(1, userEmail);
            checkenrollment.setString(2, coursename);
            ResultSet output = checkenrollment.executeQuery();

            if (output.next()) {
                return true;
            }
            else {
                return false;
            }
        } catch(SQLException e) {
            System.out.println("you are not enrolled in that course");
            return false;
        }
    }
    public void updateProgress(String userEmail, String courseName) {
        try {
            // Check if a progress record exists for the given user and course
            String sqlcheckprogress = "SELECT * FROM progress WHERE user_email = ? AND coursename = ?";
            PreparedStatement checkprogress = conn.prepareStatement(sqlcheckprogress);
            checkprogress.setString(1, userEmail);
            checkprogress.setString(2, courseName);
            ResultSet checkRs = checkprogress.executeQuery();

            if (!checkRs.next()) {
                // Insert a new progress record if it does not exist
                String sqladdprogress = "INSERT INTO progress (user_email, coursename, percentage,finalexam_percent) VALUES (?, ?, 0.0,0.0)";
                PreparedStatement addprogress = conn.prepareStatement(sqladdprogress);
                addprogress.setString(1, userEmail);
                addprogress.setString(2, courseName);
                addprogress.executeUpdate();
            }

            // Get all the quizzes for the given user and course
            String sqlgetquizzes = "SELECT * FROM trainee_quizzes WHERE email = ? AND coursename = ?";
            PreparedStatement getquizzes = conn.prepareStatement(sqlgetquizzes);
            getquizzes.setString(1, userEmail);
            getquizzes.setString(2, courseName);
            ResultSet output = getquizzes.executeQuery();

            // Calculate the total percentage
            double totalPercentage = 0.0;
            int numQuizzes = 0;
            while (output.next()) {
                String quizName = output.getString("quiz_name");
                int score = output.getInt("score");

                // Get the number of questions for the quiz
                String sqlcountquestions = "SELECT COUNT(*) FROM quizzes WHERE quiz_name = ?";
                PreparedStatement countquestions = conn.prepareStatement(sqlcountquestions);
                countquestions.setString(1, quizName);
                ResultSet questionsoutput = countquestions.executeQuery();
                if (questionsoutput.next()) {
                    int numQuestions = questionsoutput.getInt(1);
                    double percentage = ((double) score / (double) numQuestions) * 100.0;
                    totalPercentage += percentage;
                    numQuizzes++;
                }
            }

            // Calculate the overall percentage
            double overallPercentage = 0.0;
            if (numQuizzes > 0) {
                overallPercentage = totalPercentage / (double) numQuizzes;
            }
            // Get the final exam percentage and update the progress table
            String sqlgetfinalexam = "SELECT percentage FROM finalexam WHERE email = ? AND coursename = ?";
            PreparedStatement getfinalexam = conn.prepareStatement(sqlgetfinalexam);
            getfinalexam.setString(1, userEmail);
            getfinalexam.setString(2, courseName);
            ResultSet finalexamoutput = getfinalexam.executeQuery();

            if (finalexamoutput.next()) {
                int percent = finalexamoutput.getInt("percentage");
                String sqlupdateprogress = "UPDATE progress SET finalexam_percent = ? WHERE user_email = ? AND coursename = ?";
                PreparedStatement updateprogress = conn.prepareStatement(sqlupdateprogress);
                updateprogress.setDouble(1, percent);
                updateprogress.setString(2, userEmail);
                updateprogress.setString(3, courseName);
                updateprogress.executeUpdate();
            }

            // Update the progress table with the overall percentage
            String sqlupdateprogress = "UPDATE progress SET percentage = ? WHERE user_email = ? AND coursename = ?";
            PreparedStatement updateprogress = conn.prepareStatement(sqlupdateprogress);
            updateprogress.setDouble(1, overallPercentage);
            updateprogress.setString(2, userEmail);
            updateprogress.setString(3, courseName);
            updateprogress.executeUpdate();

            System.out.println("Progress updated successfully");
        } catch (SQLException e) {
            System.out.println("Error updating progress: " + e.getMessage());
        }
    }
}
