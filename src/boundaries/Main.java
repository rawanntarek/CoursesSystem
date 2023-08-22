package boundaries;
import trainee.trainee;
import trainee_management.*;
import registration.*;

import administration_management.*;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;
import course.*;
public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        AdminLoginController alogin=new AdminLoginController();
        courses course = new courses();
        trainee user = new trainee();
        loginMenu menu=new loginMenu();
        String coursename;
        int choice;
        while (true) {
            System.out.println("Welcome to the system!");
            System.out.println("Please choose an option:");
            System.out.println("1. Sign up");
            System.out.println("2. Log in");
            System.out.println("3. Log in as an Adminstrator");
            System.out.println("4.contact us");
            System.out.println("5. Exit");

            choice = input.nextInt();
            input.nextLine(); // consume newline character

            switch (choice) {
                case 1:
                    // Sign up
                    System.out.println("Please enter your username:");
                    String username = input.nextLine();
                    user.setname(username);
                    System.out.println("Please enter your password:");
                    String password= input.nextLine();
                    System.out.println("Please enter your email:");
                    String email = input.nextLine();
                    RegistrationController register=new RegistrationController();
                    register.setTraineeInfo(user,username,password,email);
                    register.signUp(user);
                    System.out.println("--------------------------------------------");
                    System.out.println(" you're redirected to the login");
                    System.out.println("Please enter your email:");
                    email = input.nextLine();
                    System.out.println("Please enter your password:");
                     password = input.nextLine();
                    menu.menu(email,password);
                    System.out.println("--------------------------------------------");
                    break;

                case 2:
                    // Log in
                    System.out.println("Please enter your email:");
                    String email1 = input.nextLine();
                    System.out.println("Please enter your password:");
                    String password1 = input.nextLine();
                    menu.menu(email1,password1);
                    break;
                case 3:
                    // Log in as Administrator
                    System.out.println("Please enter your email:");
                    String email2 = input.nextLine();
                    System.out.println("Please enter your password:");
                    String password3 = input.nextLine();
                    user.setmail(email2);
                    user.setPassword(password3);
                    if (alogin.login(email2, password3)) {
                        boolean exit = false;
                        while (!exit) {
                            System.out.println("Please choose an option:");
                            System.out.println("1. Add a new course");
                            System.out.println("2. manage existing courses");
                            System.out.println("3. Add course ratings and reviews");
                            System.out.println("4. Define course materials and prerequisites");
                            System.out.println("5. Set up quizzes");
                            System.out.println("6. Set up exams");
                            System.out.println("7. Logout");
                            System.out.println("--------------------------------------------");
                            choice = input.nextInt();
                            input.nextLine(); // consume newline character

                            switch (choice) {
                                case 1:
                                    // Adding a new course
                                    System.out.println("Please enter the name of the new course:");
                                    String courseName = input.nextLine();
                                    CourseAddition ca=new CourseAddition(courseName);
                                    ca.adding();
                                    break;
                                case 2:
                                    System.out.println("choose one of these options");
                                    System.out.println("1.edit course name");
                                    System.out.println("2.edit course rating");
                                    System.out.println("3.edit course reviews");
                                    System.out.println("4.edit course material");
                                    System.out.println("5.edit course prerequesites");
                                    System.out.println("6.delete course");
                                    System.out.println("--------------------------------------------");
                                    choice = input.nextInt();
                                    input.nextLine();
                                    switch (choice)
                                    {
                                        case 1:
                                            System.out.println("enter the course name you want to edit");
                                            coursename = input.nextLine();
                                            System.out.println("enter the new course name ");
                                            String cname2;
                                            cname2 = input.nextLine();
                                            CourseNameEditor cname = new CourseNameEditor(coursename,cname2);
                                            cname.managecourse();
                                            break;
                                        case 2:
                                            System.out.println("enter the course name");
                                            coursename=input.nextLine();
                                            System.out.println("enter the new rating ");
                                            int crating;
                                            crating=input.nextInt();
                                            CourseRatingsEditor cratings = new CourseRatingsEditor(coursename,crating);
                                            cratings.managecourse();
                                            break;
                                        case 3:
                                            System.out.println("enter the course name");
                                            coursename = input.nextLine();
                                            System.out.println("enter the new review ");
                                            String creview;
                                            creview= input.nextLine();
                                            CourseReviewsEditor creviews = new CourseReviewsEditor(coursename,creview);
                                            creviews.managecourse();
                                            break;
                                        case 4:
                                            System.out.println("enter the course name you want to edit");
                                            coursename = input.nextLine();
                                            System.out.println("enter the new course material(the file) ");
                                            File material = new File(input.nextLine());
                                            System.out.println("enter the new course material name");
                                            String cmaterialname;
                                            cmaterialname=input.nextLine();
                                            CourseMaterialEditor cmaterial = new CourseMaterialEditor(coursename,material,cmaterialname);
                                            cmaterial.managecourse();
                                            break;
                                        case 5:
                                            System.out.println("enter the course name");
                                            coursename = input.nextLine();
                                            System.out.println("enter the new prerequesites ");
                                            String prerequesites;
                                            prerequesites= input.nextLine();
                                            CoursePrerequesitesEditor cprerequesites = new CoursePrerequesitesEditor(coursename,prerequesites);
                                            cprerequesites.managecourse();
                                            break;
                                        case 6:
                                            System.out.println("enter the course name you want to delete");
                                            coursename = input.nextLine();
                                            CourseDeletion delete = new CourseDeletion(coursename);
                                            delete.managecourse();
                                            break;


                                    }
                                    break;


                                case 3:
                                    System.out.println("Please enter the name of the course:");
                                     courseName = input.nextLine();
                                    System.out.println("--------------------------------------------");
                                    System.out.println("Please enter your new course review (or press Enter to skip):");
                                    String newReview = input.nextLine();
                                    System.out.println("--------------------------------------------");
                                    System.out.println("Please enter your new course rating (or press Enter to skip):");
                                    int newRating = Integer.parseInt(input.nextLine());
                                    System.out.println("--------------------------------------------");
                                    RatingsReviewsAddition rra=new RatingsReviewsAddition(courseName,newRating,newReview);
                                    rra.adding();
                                    System.out.println("Course updated!");
                                    System.out.println("--------------------------------------------");
                                    break;

                                case 4:
                                    // Defining course materials
                                    System.out.println("Please enter the course name:");
                                    courseName = input.nextLine();
                                    System.out.println("--------------------------------------------");
                                    System.out.println("Please enter the course material(the file):");
                                    File coursematerial = new File(input.nextLine());
                                    System.out.println("--------------------------------------------");
                                    System.out.println("Please enter the course material name:");
                                    String coursematerialname= input.nextLine();
                                    System.out.println("--------------------------------------------");
                                    System.out.println("Please enter the course prerequisites :");
                                    String courseprerequisites  = input.nextLine();
                                    System.out.println("--------------------------------------------");
                                    CourseMaterialAddition cma=new CourseMaterialAddition(courseName,coursematerial,coursematerialname,courseprerequisites);
                                    cma.adding();
                                    System.out.println("--------------------------------------------");

                                    break;

                                case 5:
                                    // Setting up exams and quizzes
                                    System.out.println("Please enter the course name:");
                                     courseName = input.nextLine();
                                    System.out.println("Please enter the quiz name:");
                                     String quizName = input.nextLine();
                                    System.out.println("Please enter how many questions do you want to add:");
                                    int questions = input.nextInt();
                                    input.nextLine();
                                    String question,choice_1,choice_2;
                                    int answer;
                                    for(int i=0;i<questions;i++)
                                    {
                                        System.out.println("Please enter a question:");
                                         question = input.nextLine();
                                        System.out.println("Please enter 1st choice:");
                                         choice_1 = input.nextLine();
                                        System.out.println("Please enter 2nd choice:");
                                         choice_2 = input.nextLine();
                                        System.out.println("Please enter the correct answer:");
                                         answer = input.nextInt();
                                         input.nextLine();
                                         CourseQuizAddition cqa=new CourseQuizAddition(courseName,quizName,question,choice_1,choice_2,answer);
                                         cqa.adding();
                                    }

                                    System.out.println("--------------------------------------------");
                                    break;
                                case 6:
                                    System.out.println("Please enter the course name:");
                                    courseName = input.nextLine();
                                    System.out.println("Please enter the exam name:");
                                    String examname = input.nextLine();
                                    System.out.println("Please enter how many questions do you want to add:");
                                    int examquestions = input.nextInt();
                                    input.nextLine();
                                    String examquestion,choice1,choice2;
                                    int correctanswer;
                                    for(int i=0;i<examquestions;i++)
                                    {
                                        System.out.println("Please enter a question:");
                                        examquestion = input.nextLine();
                                        System.out.println("Please enter 1st choice:");
                                        choice1 = input.nextLine();
                                        System.out.println("Please enter 2nd choice:");
                                        choice2 = input.nextLine();
                                        System.out.println("Please enter the correct answer:");
                                        correctanswer = input.nextInt();
                                        input.nextLine();
                                        CourseExamAddition cea=new CourseExamAddition(courseName,examname,examquestion,choice1,choice2,correctanswer);
                                        cea.adding();
                                    }

                                    System.out.println("--------------------------------------------");
                                    break;

                                case 7:
                                    System.out.println("you are redirected to the main menu again");
                                    System.out.println("--------------------------------------------");
                                    exit = true;
                                    break;

                                default:
                                    System.out.println("Invalid choice. Please try again.");
                                    break;
                            }
                        }
                    }
                    break;
                case 4:
                    Helpdesk help=new Helpdesk();
                    help.ask();
                    break;
                case 5:
                    // Exit
                    System.out.println("Goodbye!");
                    System.out.println("--------------------------------------------");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}