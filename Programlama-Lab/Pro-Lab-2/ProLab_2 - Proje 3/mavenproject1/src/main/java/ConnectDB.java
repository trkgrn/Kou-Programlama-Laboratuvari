
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trkgrn
 */
public class ConnectDB {
    static String url = "jdbc:postgresql://localhost:8000/Banka";
    static Connection con = null;
    
    static void connect()
    { 
        try{
           con = DriverManager.getConnection(url,"postgres","1071");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }    
}
    
    

