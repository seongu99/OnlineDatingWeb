/*
11/26/2014 20:36 testing!!!!!
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBWorks;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.tomcat.jni.User.username;

/**
 *
 * @author Sherry, Jason, Seongu
 */
//testing
//this
public class DBConnection {
    static String mysJDBCDriver = "com.mysql.jdbc.Driver";
    static String url = "jdbc:mysql://localhost:3306/jss";
    static String username = "root";
    static String password = "jss123";

    static java.sql.Connection myConnection = null;
    static PreparedStatement myPreparedStatement = null;
    
    public static String getPassword() {
        return password;
    }
   
    public static ResultSet ExecQuery(String query){
        ResultSet myResultSet = null;
        try{
            if(myConnection == null || (myConnection !=null && !myConnection.isValid(0)))
            {
                Class.forName(mysJDBCDriver).newInstance();
                myConnection = DriverManager.getConnection(url,username,password);
            }
            myPreparedStatement = myConnection.prepareStatement(query);
            myResultSet = myPreparedStatement.executeQuery();
            
        } catch(ClassNotFoundException | SQLException e)
        {
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  myResultSet;
    }
    
    public static int ExecUpdateQuery(String query){
       int retValue = 0;
        try{
            if(myConnection == null || (myConnection !=null && !myConnection.isValid(0)))
            {
                Class.forName(mysJDBCDriver).newInstance();
                myConnection = DriverManager.getConnection(url,username,password);
            }
            myPreparedStatement = myConnection.prepareStatement(query);
            retValue = myPreparedStatement.executeUpdate();
            
        } catch(ClassNotFoundException | SQLException e)
        {
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  retValue;
    }
}
