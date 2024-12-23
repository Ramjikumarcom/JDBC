import java.sql.*;
import java.util.Scanner;
import java.util.Date;

import static java.lang.System.exit;
public class HotelManagementSystem {

//  70500260849
    private static final String url = "jdbc:mysql://127.0.0.1:3306/hoteldb";
    private static final String username = "root";
    private static final String password = "Ramjik1805";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }
        try {
            Connection con = DriverManager.getConnection(url, username, password);//To  Established Connection
            while (true) {
                System.out.println("********************************************************");
                System.out.println("1. Reservation ");
                System.out.println("2. view Details ");
                System.out.println("3. update User Details");
                System.out.println("4. Delete User Details");
                System.out.println("0. Exit ");
                Scanner sc = new Scanner(System.in);
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        Reservation(con, sc);// to store into Database
                        break;
                    case 2:
                        ViewDetails(con, sc);// view from database


                        break;
                    case 3:
                        UpdateUser(con, sc); // update in database
                        break;
                    case 4:
                        DeleteUser(con, sc);// delete from database
                        break;
                    case 0:
                        System.out.println("Thank for Choosing my application");
                        exit(0); // out from exection
                        sc.close();
                        return;
                }
            }

        } catch (SQLException e) {
            System.out.println("Connection failed");
        }

    }

    public static void Reservation(Connection con, Scanner sc) throws SQLException {

        System.out.println("Enter your name");
        String guest_name = sc.next();
        System.out.println("Enter Phone number");
        String contact_number = sc.next();
        System.out.println("Enter Room number");
        int room_number = sc.nextInt();

        try {
            String query = "insert into reservation(guest_name,room_number,contact_number)"+ "values('"+guest_name+"',"+room_number+",'"+contact_number+ " ');";
            Statement stmt = con.createStatement();
            int rowEffected = stmt.executeUpdate(query);
            if (rowEffected > 0) {
                System.out.println("Successfully Reserved your room");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void ViewDetails(Connection con, Scanner sc) throws SQLException {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from reservation");

            while (rs.next()) {
                int reservation_id = rs.getInt("reservation_id");
                String guest_name = rs.getString("guest_name");
                String contact_number = rs.getString("contact_number");
                int room_number = rs.getInt("room_number");
                Date date = rs.getDate("reservation_date");

                System.out.println("===============================");
                System.out.println("ID: " + reservation_id);
                System.out.println("Name: " + guest_name);
                System.out.println("contact_number : " + contact_number);
                System.out.println("room_number: " + room_number);
                System.out.println("Date: " + date);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void UpdateUser(Connection con, Scanner sc) throws SQLException {
        try {

            System.out.println("Enter your name");
            String name = sc.next();
            System.out.println("enter Phone number");
            String phone = sc.next();
            System.out.println("enter Room number");
            int room_number = sc.nextInt();
            System.out.println("Enter your Id");
            int id = sc.nextInt();
            Statement stmt = con.createStatement();


            String query = "UPDATE reservation SET guest_name='"+name+"',"+"room_number=" +room_number+","+"contact_number='"+ phone+"' "+"WHERE reservation_id=" +id;
            int rs = stmt.executeUpdate(query);
            if (rs > 0) {
                System.out.println("Successfully Updated your room");
            } else {
                System.out.println("Failed to update your room");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
    public static void DeleteUser(Connection con, Scanner sc) throws SQLException {
        try{

            System.out.println("Enter your Id");
           int reservation_id= sc.nextInt();

            Statement stmt = con.createStatement();
            String query = "DELETE FROM reservation WHERE reservation_id="+reservation_id;
            int rs = stmt.executeUpdate(query);
            if (rs > 0) {
                System.out.println("Successfully Deleted your room");
            }
            else{
                System.out.println("Failed to delete your room");
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


}
