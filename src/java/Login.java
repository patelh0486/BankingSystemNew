/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author LinJian
 */
@ManagedBean
@SessionScoped
public class Login {

    private String id;
    private String password;
    private OnlineAccount theLoginAccount;
    
    //get methods and set methods
    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public OnlineAccount getTheLoginAccount() {
        return theLoginAccount;
    }
    

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    public String login()
    {
        //load the Driver
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }
        
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/drlin?useSSL=false";
        Connection connection = null;  //a connection to the database
        Statement statement = null;    //execution of a statement
        ResultSet resultSet = null;   //set of results
        
        
        try
        {      
            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL, 
                    "drlin", "2019drlin");   
            statement = connection.createStatement();
            
            resultSet = statement.executeQuery("Select * from onlineaccount "
                    + "where id = '" + 
                    id + "'" );
            
            if(resultSet.next())
            {
                //id is found
                if(password.equals(resultSet.getString(3)))
                {
                    //password is good 
                    //display welcome.xhtml
                    //create an OnlineAccount object
                    theLoginAccount = new OnlineAccount(id, 
                            resultSet.getString(2), password);
                    return "welcome";
                     
                }
                else
                {
                    id = "";
                    password = "";
                    //display loginNotOK.xhtml
                    return "loginNotOK";    
                }
            }
            else
            {
                id = "";
                password = "";
                //id is not found, display loginNotOK.xhtml
                return "loginNotOK";
                 
            }
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return ("internalError");
        }
        finally
        {
            try
            {
                resultSet.close();
                statement.close();
                connection.close();
                 
            }
            catch (Exception e)
            {
                e.printStackTrace();    
            }
        }
    }
    
}
