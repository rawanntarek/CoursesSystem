package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MyJDMC {
    public static void main(String[] args) {
        try {
            // establishing connection to the data base
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/jdbc", "root", "Example@2021!");
            Statement statement = connection.createStatement();
            System.out.println("Connection established successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}