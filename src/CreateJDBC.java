import  java.sql.*;

import static java.lang.Class.forName;

public class CreateJDBC {
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
             Statement st=con.createStatement(); // create Statement Interface
             ResultSet rs=st.executeQuery(query); // store into Resultset interface
              while(rs.next()){
                  int id=rs.getInt("id");
                  String name=rs.getString("name");
                  String job_title=rs.getString("job_title");
                  double salary=rs.getDouble("salary");
                  System.out.println("===============================");
                  System.out.println("ID: "+id);
                  System.out.println("Name: "+name);
                  System.out.println("Job Title: "+job_title);
                  System.out.println("Salary: "+salary);

              }
              con.close();
              st.close();
              rs.close();
         }
         catch (SQLException e){
             System.out.println("Connecting to database failed");
             System.out.println(e.getMessage());
         }
    }
}