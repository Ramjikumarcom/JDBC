package com.BankingSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner scanner;

    public User(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void Registration() {
        scanner.nextLine();
        System.out.println("Enter your full name");
        String username = scanner.nextLine();
        System.out.println("enter Email");
        String email = scanner.nextLine();
        System.out.println("enter Password");
        String password = scanner.nextLine();
        try {
            if (user_exist(email)) {
                System.out.println("user already Exist!! please login!!!");
                return;
            } else {
                String query = "insert into user values(?,?,?)";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, username);
                pst.setString(2, email);
                pst.setString(3, password);
                int rs = pst.executeUpdate();
                if (rs > 0) {
                    System.out.println("Registration Succesfully");
                } else {
                    System.out.println("Registration Failed");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public String Login() throws SQLException {
        scanner.nextLine();
        System.out.println("Enter your email");
        String email = scanner.nextLine();
        System.out.println("Enter your password");
        String password = scanner.nextLine();
        String query = "select * from user where email=? and password=?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, email);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return email;
        } else {
            System.out.println("Please enter valid email and password");
            return null;
        }
    }


    public boolean user_exist(String email) throws SQLException {
        String query = "select * from user where email=?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }


}
