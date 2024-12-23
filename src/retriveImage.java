import java.io.*;
import java.sql.*;
import java.sql.PreparedStatement;
import static java.lang.Class.forName;

public class retriveImage {

    public static void main(String[] args) throws SQLException {
        String url="jdbc:mysql://127.0.0.1:3306/student";
        String username="root";
        String password="Ramjik1805";

        String folderpath="C:\\Downloads\\imagefromsql\\";

        String retrive="select * from imagefile where image_id=(?);";
        try{
            System.out.println("Driver loaded Successfully");
            Class.forName("com.mysql.jdbc.Driver");// for diriver loading

        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }

        try {
            System.out.println("Connection succcesfully");
            Connection con = DriverManager.getConnection(url, username, password);// establize Connection
            PreparedStatement ps = con.prepareStatement(retrive);
            ps.setInt(1,1);
            ResultSet rs = ps.executeQuery();
          if(rs.next()){
                byte[] imagedata=rs.getBytes("image_data");
                String imagepath=folderpath+"Ramji.jpg";

              OutputStream outputStream=new FileOutputStream(imagepath);
              outputStream.write(imagedata);
            }
          else{
              System.out.println("image not found");
          }
        }
        catch (SQLException e){
            System.out.println("Connecting to database failed");
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
