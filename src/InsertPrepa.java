import java.sql.*;
import java.util.Scanner;
//Same as for delete and update
import static java.lang.Class.forName;

public class InsertPrepa {

    public static void main(String[] args) throws SQLException {
        String url="jdbc:mysql://127.0.0.1:3306/student";
        String username="root";
        String password="Ramjik1805";

        String Insert="insert into Employee values(?,?,?,?)";
        String Update="UPDATE Employee SET name='Ramji Kumar', Salary=150000 ,job_title='Software Developer' WHERE id=4"; // for Updateing the data
        String DeleteQuery="Delete from Employee where id=1;"; // for Deleting Data

        try{
            System.out.println("Driver loaded Successfully");
            Class.forName("com.mysql.jdbc.Driver");// for diriver loading

        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }

        try{
            System.out.println("Connection succcesfully");
            Connection con=DriverManager.getConnection(url,username,password);// establize Connection

            PreparedStatement ps=con.prepareStatement(Insert); // for Select Statement



//*************************************Taking from user ********************
            System.out.println("Enter data ");
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter id:");
            int id=sc.nextInt();
            System.out.println("Enter name:");
            String name=sc.next();
            System.out.println("Enter job_title");
            String job_title=sc.next();
            System.out.println("Enter salary:");
            double salary=sc.nextDouble();

            ps.setInt(1,id);
            ps.setString(2,name);
            ps.setString(3,job_title);
            ps.setDouble(4,salary);

// *********************** setting manualally data *******************************************
//      ps.setInt(1,1);
//      ps.setString(2,"Rohit Kumar");
//      ps.setString(3,"Watch Mistri");
//      ps.setDouble(4,809099);

//   **************************************************************************************************


            int rs=ps.executeUpdate();
      if(rs>0){
          System.out.println("Insert Successfully");
      }
      else{
          System.out.println("Insert Failed");
      }
//
            ps.close();

            con.close();

            System.out.println("Connection closed Successfully");
        }
        catch (SQLException e){
            System.out.println("Connecting to database failed");
            System.out.println(e.getMessage());
        }

    }
}
