package com.BankingSystem;

import java.sql.*;
import java.util.Scanner;

public class BankingApp {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/banking_system";
    private static final String username = "root";
    private static final String password = "Ramjik1805";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {


        try {
            System.out.println("Driver loaded Successfully");
            Class.forName("com.mysql.jdbc.Driver");// for diriver loading

        } catch (ClassNotFoundException e) {
            System.out.println("Driver Not Found");
            System.out.println(e.getMessage());


        }

        try {
            System.out.println("Connection succcesfully");
            Connection con = DriverManager.getConnection(url, username, password);// establize Connection

            Scanner scanner = new Scanner(System.in);
//          creating the object of all the class
            User user = new User(con, scanner);
            Accounts accounts = new Accounts(con, scanner);
            AccountManager accountManager = new AccountManager(con, scanner);
            String email;
            long account_number;
            while (true) {
                System.out.println("****WELCOME TO BANKING SYSTEM***");
                System.out.println();
                System.out.println("1.Register");
                System.out.println("2.Login");
                System.out.println("3.Exit");
                int Entrychoice = scanner.nextInt();
                switch (Entrychoice) {
                    case 1:
                        user.Registration();
                        break;
                    case 2:
                        email = user.Login();
                        if (email != null) {
                            System.out.println();
                            System.out.println("User Logged In Successfully");
                            if (!accounts.IsExistAccount(email)) {
                                System.out.println("1.Open Account");
                                System.out.println("2.Exit");
                                if (scanner.nextInt() == 1) {
                                    account_number = accounts.OpenAccount(email);
                                    System.out.println(" Congratulations!!! Account Opened successfully");
                                    System.out.println("your Account number is:" + account_number);

                                } else break;
                            }
                            account_number = accounts.getAccountNumber(email);
                            int choice2 = 0;
                            while (choice2 != 5) {
                                System.out.println("1.Debit Money");
                                System.out.println("2.Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4.Check Balance");
                                System.out.println("5. Logout");
                                choice2 = scanner.nextInt();
                                switch (choice2) {
                                    case 1:
                                        accountManager.debitMoney(account_number);
                                        break;
                                    case 2:
                                        accountManager.CreditMoney(account_number);
                                        break;
                                    case 3:
                                        accountManager.TransferMoney(account_number);
                                        break;

                                    case 4:
                                        accountManager.getBalance(account_number);
                                        break;
                                    case 5:
                                        System.out.println("Thank you for using Banking System");
                                        break;
                                    default:
                                        System.out.println("Invalid choice");
                                        break;
                                }
                            }
                        }
                        else{
                            System.out.println("Enter valid Email and Password");
                        }


                    case 3:
                        System.out.println("Thank you for using Banking System");
                        System.out.print("Exiting System");
                        for (int i=0;i<5;i++){
                            System.out.print(".");
                        }
                        return;
                    default:
                        System.out.println("Invalid Entry");
                        break;
                }

            }


        } catch (SQLException e) {
            System.out.println("Connecting to database failed");
            System.out.println(e.getMessage());
        }
    }
}
