import java.sql.*;
import java.util.Scanner;

public class transaction2 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/transaction";
        String username = "root";
        String password = "Ramjik1805";

        String Query = "SELECT balance FROM accounsts WHERE account_number=?";
        String Update = "UPDATE accounsts SET balance=? WHERE account_number=?";

        try {
            System.out.println("Loading Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to Database...");
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful!");

            Scanner sc = new Scanner(System.in);

            System.out.println("Enter your Account Number:");
            String account = sc.nextLine();

            System.out.println("Enter Account Number to Transfer To:");
            String CoAccount = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(Query);
            ps.setString(1, account);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int SenderBalance = rs.getInt("balance");

                PreparedStatement ps2 = con.prepareStatement(Query);
                ps2.setString(1, CoAccount);
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    int CoAccount2Balance = rs2.getInt("balance");

                    System.out.println("Enter Amount to Transfer:");
                    int Amount = sc.nextInt();

                    if (SenderBalance >= Amount) {
                        // Update the recipient's balance
                        PreparedStatement updatePs = con.prepareStatement(Update);
                        updatePs.setInt(1, CoAccount2Balance + Amount);
                        updatePs.setString(2, CoAccount);
                        updatePs.executeUpdate();

                        // Update the sender's balance
                        updatePs.setInt(1, SenderBalance - Amount);
                        updatePs.setString(2, account);
                        updatePs.executeUpdate();

                        System.out.println("Transfer successful!");
                    } else {
                        System.out.println("Insufficient balance. Your balance is: " + SenderBalance);
                    }
                } else {
                    System.out.println("No data for the recipient's account number.");
                }
            } else {
                System.out.println("No data for your account number.");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database connection or query failed: " + e.getMessage());
        }
    }
}
