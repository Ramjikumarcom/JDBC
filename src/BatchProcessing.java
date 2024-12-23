import  java.sql.*;

import static java.lang.Class.forName;

public class BatchProcessing {
    public static void main(String[] args) throws ClassNotFoundException {

        String url="jdbc:mysql://127.0.0.1:3306/student";
        String username="root";
        String password="Ramjik1805";
        String query="select * from Employee;";
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
           Statement st=con.createStatement();
           st.addBatch("insert into Employee(id,name,job_title,salary) values(5,'A','Shopkepper',150000.0);");
            st.addBatch("insert into Employee(id,name,job_title,salary) values(6,'B','Hr Manager',15300.0);");
            st.addBatch("insert into Employee(id,name,job_title,salary) values(7,'C','Deovps',12300.0);");
            st.addBatch("insert into Employee(id,name,job_title,salary) values(8,'D','Apple Engineer',2345000.0);");
          int [] resultBatch=  st.executeBatch();
          con.commit();
            System.out.println("Successfully Inserted Batch");

        }
        catch (SQLException e){
            System.out.println("Connecting to database failed");
            System.out.println(e.getMessage());
        }
    }
}