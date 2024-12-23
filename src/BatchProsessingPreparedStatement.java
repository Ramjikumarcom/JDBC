import  java.sql.*;
import java.util.Scanner;

import static java.lang.Class.forName;

public class BatchProsessingPreparedStatement {
    public static void main(String[] args) throws ClassNotFoundException {

        String url="jdbc:mysql://127.0.0.1:3306/student";
        String username="root";
        String password="Ramjik1805";

        try {

            System.out.println("Driver loaded Successfully");
            Class.forName("com.mysql.jdbc.Driver");// for diriver loading

        } catch (ClassNotFoundException e) {
            System.out.println("Driver Not Found");
            System.out.println(e.getMessage());


        }

        try{
            System.out.println("Connection succcesfully");
            Connection con=DriverManager.getConnection(url,username,password);// establize Connection
            con.setAutoCommit(false);

            String Query="insert into Employee values(?,?,?,?)";
            PreparedStatement pst=con.prepareStatement(Query);
            Scanner sc=new Scanner(System.in);
            while(true){
                System.out.println("Enter Employee Id");
                int id=sc.nextInt();
                System.out.println("Enter Employee Name");
                String name=sc.next();
                System.out.println("Enter Job Title");
                String job=sc.next();
                System.out.println("Enter Salary");
                double salary=sc.nextDouble();
                pst.setInt(1,id);
                pst.setString(2,name);
                pst.setString(3,job);
                pst.setDouble(4,salary);
                pst.addBatch();

                System.out.println("Do you Want to ADD More Data Press(Y/N)");
               String decesion= sc.next();
               if(decesion.toUpperCase().equals("N")){
                   break;
               }

            }

         int [] ExecuteBatch= pst.executeBatch();// use for checking which query is execute or which is not (1->execute,0->not execute)
            con.commit();
            System.out.println("Data Added Successfully");

        }
        catch (SQLException e){
            System.out.println("Connecting to database failed");
            System.out.println(e.getMessage());
        }
    }
}
