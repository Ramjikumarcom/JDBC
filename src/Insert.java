import java.sql.*;

import static java.lang.Class.forName;

public class Insert {

    public static void main(String[] args) throws SQLException {
        String url="jdbc:mysql://127.0.0.1:3306/student";
        String username="root";
        String password="Ramjik1805";
        String query="insert into Employee(id,name,job_title,salary) values(4,'Rakesh Kumar','Shopkepper',15000.0);";
        try{
            System.out.println("Driver loaded Successfully");
            Class.forName("com.mysql.jdbc.Driver");// for diriver loading

        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }

        try{
            System.out.println("Connection succcesfully");
            Connection con=DriverManager.getConnection(url,username,password);// establize Connection
            Statement st=con.createStatement(); // create Statement Interface
           int rowsEffected=  st.executeUpdate(query);
             if(rowsEffected>0){
                 System.out.println("Insert Successfully."+rowsEffected+" rows effected");
             }
             else{
                 System.out.println("Insertion Failed");
             }
            con.close();
            st.close();
            System.out.println("Connection closed Successfully");
        }
        catch (SQLException e){
            System.out.println("Connecting to database failed");
            System.out.println(e.getMessage());
        }

    }
}