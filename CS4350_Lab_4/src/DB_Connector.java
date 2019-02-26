import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class DB_Connector {
    Connection connection;

    DB_Connector() {
        connect_to_database();
    }
     public void connect_to_database() {
        try{
            System.out.println("Connecting to Database. . .");
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/Lab4","root","root");
            System.out.println("Database connected");

        }catch(Exception e) {
            System.out.println("Something went wrong!");
            System.out.println(e);
        }
    }
}
