package administration_management;

import java.sql.*;

public class CourseQuizAddition implements ICourseAddition {
    private static Connection conn;
    String url = "jdbc:mysql://127.0.0.1:3307/jdbc";
    String username = "root";
    String password = "Example@2021!";
    String courseName;
    String quizname;
    String question;
    String option_1;
    String option_2;
    int correct_option;

    public CourseQuizAddition(String courseName, String quizname, String question, String option_1, String option_2, int correct_option)
    {
        this.courseName=courseName;
        this.quizname=quizname;
        this.question=question;
        this.option_1=option_1;
        this.option_2=option_2;
        this.correct_option=correct_option;
    }
    @Override
    public void adding() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sqlcheckCourse = "SELECT COUNT(*) FROM courses WHERE course_name = ?";
            PreparedStatement checkcourse = conn.prepareStatement(sqlcheckCourse);
            checkcourse.setString(1, courseName);
            ResultSet rs = checkcourse.executeQuery();
            if (!rs.next() || rs.getInt(1) == 0) {
                System.out.println("Error: Course not found in the courses database.");
                return;
            }
            String sqlinsertQuiz = "INSERT INTO quizzes (coursename, quiz_name,questions,choice1,choice2,answer) VALUES ((SELECT course_name FROM courses WHERE course_name=?), ?,?,?,?,?)";
            PreparedStatement insertquiz = conn.prepareStatement(sqlinsertQuiz);
            insertquiz.setString(1, courseName);
            insertquiz.setString(2, quizname);
            insertquiz.setString(3, question);
            insertquiz.setString(4, option_1);
            insertquiz.setString(5, option_2);
            insertquiz.setInt(6, correct_option);
            insertquiz.executeUpdate();
            System.out.println("Quiz are added successfully!");
            System.out.println("--------------------------------------------");
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }
}
