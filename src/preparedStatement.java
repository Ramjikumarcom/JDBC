import java.sql.*;

import static java.lang.Class.forName;

public class preparedStatement {

    public static void main(String[] args) throws SQLException {
        String url="jdbc:mysql://127.0.0.1:3306/student";
        String username="root";
        String password="Ramjik1805";
        String query="SELECT  * from Employee where name=? and salary=? and id=?" ;
        String Insert="insert into student values(?,?,?,?)";
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


//
//            // execute on the basis of condition
//            ps.setString(1,"Ramji kumar");
//            ps.setDouble(2,150000.0);
//            ps.setInt(3,4);

//            ResultSet rs=ps.executeQuery();
//            while(rs.next()){
//                int id=rs.getInt("id");
//                String name=rs.getString("name");
//                String job_tittle=rs.getString("job_title");
//
//                double salary=rs.getDouble("salary");
//                System.out.println("your Data is :");
//                System.out.println("id: "+id);
//                System.out.println("name: "+name);
//                System.out.println("job_title: "+job_tittle);
//                System.out.println("salary: "+salary);
//
//            }
            ps.close();
//            rs.close();
            con.close();

            System.out.println("Connection closed Successfully");
        }
        catch (SQLException e){
            System.out.println("Connecting to database failed");
            System.out.println(e.getMessage());
        }

    }
}
