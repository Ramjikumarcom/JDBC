import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.sql.PreparedStatement;
import static java.lang.Class.forName;

public class ImageFile {

    public static void main(String[] args) throws SQLException {
        String url="jdbc:mysql://127.0.0.1:3306/student";
        String username="root";
        String password="Ramjik1805";
        String Imagepath="C:\\Downloads\\MT2024123.jpg";

        String query="insert into imagefile (image_data) values(?); ";

        try{
            System.out.println("Driver loaded Successfully");
            Class.forName("com.mysql.jdbc.Driver");// for diriver loading

        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }

        try {
            System.out.println("Connection succcesfully");
            Connection con = DriverManager.getConnection(url, username, password);// establize Connection

            FileInputStream fileInputStream=new FileInputStream(Imagepath); // convert image into binary format
             byte[] imageData=new byte[fileInputStream.available()];

            fileInputStream.read(imageData); // store into array

            // inserting into database******************************

            PreparedStatement preparedStatement=con.prepareStatement(query); // creating instance of interface
            preparedStatement.setBytes(1, imageData);// to set the data
            int affectedRows=preparedStatement.executeUpdate(); //

            if(affectedRows>0){
                System.out.println("Insert Successfully");

            }
            else{
                System.out.println("Insert Failed");
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
