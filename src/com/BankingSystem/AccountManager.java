package com.BankingSystem;

import java.sql.*;
import java.util.Scanner;

public class AccountManager {
    private static Connection connection;
    private static Scanner scanner;

    AccountManager(Connection connection, Scanner scanner) {
        AccountManager.connection = connection;
        AccountManager.scanner = scanner;
    }

    public void debitMoney(Long accountNumber) {
        scanner.nextLine();
        System.out.println("Enter Amount to Debit:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter Security Pin:");
        String securityPin = scanner.nextLine();

        String query = "SELECT balance FROM accounts WHERE account_number = ? AND security_pin = ?";
        String updateQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";

        try (PreparedStatement selectStmt = connection.prepareStatement(query);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            connection.setAutoCommit(false);

            selectStmt.setLong(1, accountNumber);
            selectStmt.setString(2, securityPin);

            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    double balance = rs.getDouble("balance");

                    if (amount <= balance) {
                        updateStmt.setDouble(1, amount);
                        updateStmt.setLong(2, accountNumber);

                        if (updateStmt.executeUpdate() > 0) {
                            connection.commit();
                            System.out.println("Rs" + amount + " debited successfully! Have a nice day!");
                        } else {
                            connection.rollback();
                            throw new RuntimeException("Debit failed.");
                        }
                    } else {
                        System.out.println("Insufficient Balance. Your Balance is: Rs" + balance);
                    }
                } else {
                    System.out.println("Invalid Account Number or Security Pin.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Failed to reset auto-commit: " + e.getMessage());
            }
        }
    }

    public void CreditMoney(Long accountNumber) {
        scanner.nextLine();
        System.out.println("Enter Amount to Credit:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter Security Pin:");
        String securityPin = scanner.nextLine();

        String query = "SELECT balance FROM accounts WHERE account_number = ? AND security_pin = ?";
        String updateQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

        try (PreparedStatement selectStmt = connection.prepareStatement(query);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            connection.setAutoCommit(false);

            selectStmt.setLong(1, accountNumber);
            selectStmt.setString(2, securityPin);

            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    updateStmt.setDouble(1, amount);
                    updateStmt.setLong(2, accountNumber);

                    if (updateStmt.executeUpdate() > 0) {
                        connection.commit();
                        System.out.println("Rs" + amount + " credited successfully! Have a nice day!");
                    } else {
                        connection.rollback();
                        throw new RuntimeException("Credit failed.");
                    }
                } else {
                    System.out.println("Invalid Account Number or Security Pin.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Failed to reset auto-commit: " + e.getMessage());
            }
        }
    }

    public void TransferMoney(Long senderAccountNumber) {
        scanner.nextLine();
        System.out.println("Enter Receiver Account Number:");
        Long receiverAccountNumber = scanner.nextLong();
        System.out.println("Enter Amount to Transfer:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter your Security Pin:");
        String securityPin = scanner.nextLine();

        String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND security_pin = ?";
        String creditQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        String checkBalanceQuery = "SELECT balance FROM accounts WHERE account_number = ?";

        try (PreparedStatement debitStmt = connection.prepareStatement(debitQuery);
             PreparedStatement creditStmt = connection.prepareStatement(creditQuery);
             PreparedStatement checkStmt = connection.prepareStatement(checkBalanceQuery)) {

            connection.setAutoCommit(false);

            // Check sender's balance
            checkStmt.setLong(1, senderAccountNumber);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    double balance = rs.getDouble("balance");


                    if (balance >= amount) {
                        // Perform Debit
                        debitStmt.setDouble(1, amount);
                        debitStmt.setLong(2, senderAccountNumber);
                        debitStmt.setString(3, securityPin);

                        int debitResult = debitStmt.executeUpdate();


                        if (debitResult > 0) {
                            // Perform Credit
                            creditStmt.setDouble(1, amount);
                            creditStmt.setLong(2, receiverAccountNumber);

                            int creditResult = creditStmt.executeUpdate();


                            if (creditResult > 0) {
                                connection.commit();
                                System.out.println("Rs" + amount + " transferred successfully!");
                            } else {
                                connection.rollback();
                                throw new RuntimeException("Credit operation failed.");
                            }
                        } else {
                            connection.rollback();
                            throw new RuntimeException("Debit operation failed.");
                        }
                    } else {
                        System.out.println("Insufficient Balance. Your Balance is: Rs" + balance);
                    }
                } else {
                    System.out.println("Sender Account does not exist.");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Failed to reset auto-commit: " + e.getMessage());
            }
        }
    }


    public void getBalance(Long accountNumber) {
        scanner.nextLine();
        System.out.println("Enter Security Pin:");
        String securityPin = scanner.nextLine();

        String query = "SELECT balance FROM accounts WHERE account_number = ? AND security_pin = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, accountNumber);
            stmt.setString(2, securityPin);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Your balance is: Rs" + rs.getDouble("balance"));
                } else {
                    System.out.println("Invalid Account Number or Security Pin.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
