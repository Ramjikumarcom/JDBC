package com.BankingSystem;

import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public long OpenAccount(String Email) throws SQLException {
        if (!IsExistAccount(Email)) {
            String query = "insert into accounts values(?,?,?,?,?)";
            scanner.nextLine();
            System.out.println("Enter full name");
            String FullName = scanner.nextLine();
            System.out.println("enter Initial Ammount");

            double Ammount = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter Securiy pin");
            String SecuriyPin = scanner.nextLine();
            try {
                Long Account_number = GenerateAccountNumber();// function

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, Account_number);
                preparedStatement.setString(2, FullName);
                preparedStatement.setString(3, Email);
                preparedStatement.setDouble(4, Ammount);
                preparedStatement.setString(5, SecuriyPin);
                int rs = preparedStatement.executeUpdate();
                if (rs > 0) {
                    System.out.println("Account Opened Successfully");
                    return Account_number;
                } else {
                    throw new RuntimeException();
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        }
        throw new RuntimeException();

    }

    public long getAccountNumber(String Email) throws SQLException {

        if (IsExistAccount(Email)) {
            String Query = "Select account_number from accounts where email=?;";
            PreparedStatement ps = connection.prepareStatement(Query);
            ps.setString(1, Email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("account_number");
            }

        } else {

            throw new SQLException("your account does not exist with this email"+Email);

        }
        throw new RuntimeException();
    }

    public long GenerateAccountNumber() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("select account_number from accounts order by account_number desc limit 1;");
        if (resultSet.next()) {
            return (resultSet.getLong("account_number") + 1);
        } else {
            return 100002;
        }
    }

    public boolean IsExistAccount(String Email) throws SQLException {
        String Query = "select * from accounts where email=?";
        PreparedStatement pstmt = connection.prepareStatement(Query);
        pstmt.setString(1, Email);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return true;
        }
        return false;

    }

}
