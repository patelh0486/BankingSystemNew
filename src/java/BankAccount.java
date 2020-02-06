
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LinJian
 */
public class BankAccount {
    
    private String ssn;
    private String accountNumber;
    private double balance;
    //private String bankerID;
    private String activityStatement;

    //Construtor
    public BankAccount (String number, String aSSN, double aBalance, String s)
    {
        
        ssn = aSSN;
        accountNumber = number;
        if(aBalance < 0)
        {
            balance = 0.0;
        }
        else
        {
            balance = aBalance;
        }
        
        activityStatement = s;
        
        
    }
    

    //deposit method
    public void deposit(double depositAmount)
    {
        if(depositAmount > 0.0)
        {
            balance = balance + depositAmount;
            DecimalFormat df = new DecimalFormat("##.00");
            activityStatement = activityStatement + DateAndTime.DateTime() 
                    + ": Deposit " 
                    + df.format(depositAmount) + ". Balance: $" 
                    + df.format(balance) + "\n";
            
            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/drlin?useSSL=false";
            
            Connection connection = null;  //a connection to the database
            Statement statement = null;    //execution of a statement
            //we do not need ResultSet in this case because no results returned
            //ResultSet resultSet = null;   //set of results
            
            try 
            {
                //connect to the database
                connection = DriverManager.getConnection(DATABASE_URL, 
                        "drlin", "2019drlin");
                
                //create a statement
                statement = connection.createStatement();
                
                int r = statement.executeUpdate("Update bankAccount set "
                        + "balance = '" +  balance + "' where accountNumber= '" 
                        + accountNumber + "'");
                r = statement.executeUpdate("Update bankAccount set "
                        + "Statement = '" +  activityStatement 
                        + "' where accountNumber= '" + accountNumber + "'");
            }
            catch (SQLException e)
            {                      
                e.printStackTrace();
             }
             finally
             {
                try
                {                     
                    statement.close();
                    connection.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();         
                }
             }
             System.out.println();
        }
    }

    //withdraw method
    public void withdraw(double withdrawAmount)
    {
        if((balance-withdrawAmount) >= 0.0)
        {
            balance = balance - withdrawAmount;
            DecimalFormat df = new DecimalFormat("##.00");
            activityStatement = activityStatement + DateAndTime.DateTime() 
                    + ": Withdraw " 
                    + df.format(withdrawAmount) + ". Balance: $" 
                    + df.format(balance) + "\n";
            
            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/drlin?useSSL=false";
            
            Connection connection = null;  //a connection to the database
            Statement statement = null;    //execution of a statement
            //we do not need ResultSet in this case because no results returned
            //ResultSet resultSet = null;   //set of results
            
            try 
            {
                //connect to the database
                connection = DriverManager.getConnection(DATABASE_URL, 
                        "drlin", "2019drlin");
                
                //create a statement
                statement = connection.createStatement();
                
                int r = statement.executeUpdate("Update bankAccount set "
                        + "balance = '" +  balance + "' where accountNumber= '" 
                        + accountNumber + "'");
                r = statement.executeUpdate("Update bankAccount set "
                        + "Statement = '" +  activityStatement + 
                        "' where accountNumber= '" + accountNumber + "'");
            }
            catch (SQLException e)
            {
                      
                e.printStackTrace();
             }
             finally
             {
                try
                {                   
                    statement.close();
                    connection.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();         
                }
             }
             System.out.println();
        }
        else
            System.out.println("The balance is not enough!");
    }

    // get current balance
    public double getBalance()
    {
        return balance;
    }

     
    //get SSN
    public String getSSN()
    {
        return ssn;
    }
    
    //get the Account Number
    public String getAccountNumber()
    {
        return accountNumber;
    }
    
    
    public List<String> showStatement()
    {
        //split the statement String into individual activities (Strings)
        List statements = new ArrayList<String> ();
        String[] statement =  activityStatement.split("\n"); 

        for(int i=0; i<statement.length; i++)
        {
            statements.add(statement[i]);
        }
        
        return statements;
    }
    
}
