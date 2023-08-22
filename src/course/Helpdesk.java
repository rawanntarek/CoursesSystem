package course;
import java.util.Scanner;

public class Helpdesk {
    public  void ask() {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Helpdesk! How may we assist you?");

        while (true) {
            String question;
            question=input.nextLine();
            System.out.println("our Api provider "+ "https://www.tawk.to/");
            String response = getResponse();
            System.out.println(response);
        }
    }

    public String getResponse() {
        return "Thank you for your question. Our team is currently looking into it and will get back to you shortly.";
    }
}
