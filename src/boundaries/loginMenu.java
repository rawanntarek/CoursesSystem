package boundaries;
import registration.*;
import trainee.*;
import course.*;
import SearchingTechniques.*;
import trainee_management.*;
import java.sql.SQLException;
import java.util.Scanner;

public class loginMenu {
    void menu(String email1,String password1) throws SQLException {
        Scanner input = new Scanner(System.in);
        courses course = new courses();
        trainee tr = new trainee();
        RegistrationController r = new RegistrationController();
        TraineeProfileController tp=new TraineeProfileController();
        CourseEnrollmentController ce=new CourseEnrollmentController();
        CourseMaterial_QuizzesController cmq=new CourseMaterial_QuizzesController();
        CourseCompletionController cce=new CourseCompletionController();
        int choice;

        tr.setmail(email1);
        tr.setPassword(password1);
        if (r.login(email1, password1)) {
            System.out.println("--------------------------------------------");
            boolean exit = false;
            while (!exit) {
                System.out.println("Please choose an option:");
                System.out.println("1. Edit Profile");
                System.out.println("2.view profile");
                System.out.println("3.search");
                System.out.println("4.enroll in a course");
                System.out.println("5.My courses");
                System.out.println("6.My progress");
                System.out.println("7.contact us");
                System.out.println("8.log out");
                choice = input.nextInt();
                input.nextLine(); // consume newline character

                switch (choice) {
                    case 1:
                        // Edit Profile
                        System.out.println("Please enter your new name (or press Enter to skip):");
                        String newname = input.nextLine();
                        System.out.println("Please enter your new pw (or press Enter to skip):");
                        String newpw = input.nextLine();
                        tp.editprofile(newname, newpw, email1);
                        System.out.println("Profile updated!");
                        System.out.println("--------------------------------------------");

                        break;
                    case 2:
                        tp.viewprofile(email1);
                        System.out.println("--------------------------------------------");
                        break;
                    case 3:
                        System.out.println("please choose a searching method:");
                        System.out.println("1-search by rating");
                        System.out.println("2-search by reviews");
                        choice = input.nextInt();
                        input.nextLine();
                        switch (choice) {
                            case 1:
                                System.out.println("search by rating");
                                int rate;
                                rate = input.nextInt();
                                Isearchingmethod searchrate = new RatingSearcher(rate);
                                searchrate.search();
                                break;
                            case 2:
                                System.out.println("search by reviews");
                                String reviews;
                                reviews = input.nextLine();
                                Isearchingmethod searchrev = new ReviewsSearcher(reviews);
                                searchrev.search();
                                break;
                        }
                        break;
                    case 4:
                        System.out.println("firstly search for a course");
                        System.out.println("please choose a searching method:");
                        System.out.println("1-search by name");
                        System.out.println("2-search by reviews");
                        System.out.println("3-search by ratings");
                        choice = input.nextInt();
                        input.nextLine();
                        switch (choice) {
                            case 1:
                                System.out.println("search by name");
                                String name;
                                name = input.nextLine();
                                Isearchingmethod searchname = new NameSearcher(name);
                                searchname.search();
                                break;
                            case 2:
                                System.out.println("search by reviews");
                                String reviews;
                                reviews = input.nextLine();
                                Isearchingmethod searchrev = new ReviewsSearcher(reviews);
                                searchrev.search();
                                break;
                            case 3:
                                System.out.println("search by rating");
                                int rate;
                                rate = input.nextInt();
                                input.nextLine();
                                Isearchingmethod searchrate = new RatingSearcher(rate);
                                searchrate.search();
                                break;

                        }
                        System.out.print("Enter the name of the course you want to enroll in");
                        String coursename = input.nextLine();
                        ce.course_enrollment(course, email1, coursename);
                        break;
                    case 5:
                        ce.viewEnrolledCourses(email1);
                        System.out.println("choose one of the following options");
                        System.out.println("1.withdraw from a certain course");
                        System.out.println("2.Interact with a course");
                        choice = input.nextInt();
                        input.nextLine();
                        String enrolled;
                        switch (choice) {
                            case 1:
                                System.out.println("enter the course name you want to withdraw from");
                                enrolled = input.nextLine();
                                cce.withdraw_fromcourse(course, email1, enrolled);
                                System.out.println("--------------------------------------------");
                                break;
                            case 2:
                                System.out.println("enter the course name you want to interact with");
                                enrolled = input.nextLine();
                                if (ce.isEnrolled(enrolled, email1)) {
                                    System.out.println("choose on of these options");
                                    System.out.println("1-take a quiz");
                                    System.out.println("2-take an exam");
                                    System.out.println("3-view course material");
                                    System.out.println("4-complete course");
                                    choice = input.nextInt();
                                    input.nextLine();
                                    switch (choice) {
                                        case 1:
                                            cmq.viewQuizzes(enrolled);
                                            System.out.println("please enter the name of the quiz you want to solve");
                                            String quizname = input.nextLine();
                                            cmq.solveQuizzes(enrolled, quizname, email1);
                                            ce.updateProgress(email1, enrolled);
                                            break;
                                        case 2:
                                            cmq.viewExams(enrolled);
                                            System.out.println("please enter the name of the exam you want to solve");
                                            String examname=input.nextLine();
                                            cmq.solveexams(enrolled,examname,email1);
                                            ce.updateProgress(email1,enrolled);
                                            break;

                                        case 3:
                                            cmq.viewcoursematerial(enrolled);
                                            System.out.println("--------------------------------------------");
                                            break;
                                        case 4:
                                            System.out.println("--------------------------------------------");
                                            if(!cce.isCourseCompleted(enrolled,email1))
                                            {
                                                    System.out.println("course not completed");

                                            }
                                            else {
                                                System.out.println("do you want to request certificate? enter 1 for yes and 2 for no");
                                                choice = input.nextInt();
                                                    switch (choice) {
                                                    case 1:
                                                cce.requestCertificate(enrolled, email1);
                                                break;
                                            case 2:
                                                break;
                                             }
                                            }
                                            break;
                                } }else {
                                    System.out.println("you are not enrolled in that course");
                                    System.out.println("--------------------------------------------");
                                }
                                break;
                        }
                        break;
                    case 6:
                        ce.viewEnrolledCourses(email1);
                        System.out.println("enter the course name you want to see your progress in");
                        String cname = input.nextLine();
                        if (ce.isEnrolled(cname, email1)) {
                            cmq.viewprogress(email1, cname);
                        } else {
                            System.out.println("you are not enrolled in that course");
                            System.out.println("--------------------------------------------");
                        }
                        break;
                    case 7:
                        Helpdesk help=new Helpdesk();
                        help.ask();
                        break;
                    case 8:

                        System.out.println("you are redirected to the main menu again");
                        System.out.println("--------------------------------------------");
                        exit = true;
                        break;


                }
            }

        } else {
            System.out.println("Invalid email or password.");
        }
    }
}