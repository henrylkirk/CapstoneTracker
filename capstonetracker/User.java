package capstonetracker;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
/**
* Class the stores values for users in capstoneProjects DB
* ISTE330 Team Project
* Ryan Sweeney, Henry Kirk, Zhimin Lin
*/

public class User  
{
   private int userID;
   private String userType;
   private String userName;
   private String password;
   private String email;
   private String fName;
   private String lName;
   private String phone;
   private String office;
   private connectDB dbConn;
   private ArrayList<String> projectIds = new ArrayList<String>();
   
   /**
    * Constructor that sets username and password
    * @param _userName the username for this user
    * @param _password the password for this user
    */
   public User(String _userName, String _password){
      userID = 0;
      userType = "NULL";
      userName = _userName;
      password = _password;
      email = "NULL";
      fName = "NULL";
      lName = "NULL";
      phone = "NULL";
      office = "NULL";
      dbConn = new connectDB();
      try {
         java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
         byte[] array = md.digest(password.getBytes());
         StringBuffer sb = new StringBuffer();
         for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
         }
         password = sb.toString();
      } catch (java.security.NoSuchAlgorithmException e) {
         e.printStackTrace();
      }
   }
   
   /**
    * Constructor that sets all values for user object
    * @param _userName username for this user record
    * @param _password password for this user record
    * @param _fName first name for this user
    * @param _lname last name for this user
    * @param _email email for this user record
    * @param _office the office number for this user
    * @param _userType userType for this user record
    */
   public User(String _userName, String _password, String _fName, String _lName, String _email, String _office, String _phone, String _userType)
   {
      userID = 0;
      userType = _userType;
      userName = _userName;
      password = _password;
      email = _email;
      fName = _fName;
      lName = _lName;
      phone = _phone;
      office = _office;
      dbConn = new connectDB();
      try {
         java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
         byte[] array = md.digest(password.getBytes());
         StringBuffer sb = new StringBuffer();
         for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
         }
         password = sb.toString();
      } catch (java.security.NoSuchAlgorithmException e) {
         e.printStackTrace();
      }
   }
   
   /**
    * Method that post new users to db
    * @return posted boolean that determines if insert was successful
    */
   public boolean registerAccount()
   {
      String statement = 
         "INSERT INTO people VALUES(?,?,?,?,?,?,?,?,?);";
      boolean posted = false;
      
      try{
         dbConn.connect();
         String getMaxUserId = "SELECT MAX(uid) FROM people;";
         ArrayList<ArrayList<String>> rs = dbConn.getData(getMaxUserId);
         userID = Integer.parseInt(rs.get(0).get(0)) + 1;
         if(dbConn.setData(statement,String.valueOf(userID),userType,userName,password,email,fName,lName,phone,office)){
            dbConn.close();
            posted = true;
         }
         else{
            dbConn.close();
            posted = false;
         }
      }
      catch(DLException dle){
         System.out.println("*** Error: " + dle.getMessage() + " ***\n");
         dle.printStackTrace();
      }
      return posted;
   }
   
   /**
    * Method the checks if user exists in db and returns boolean
    * @return loginSuccess boolean that determines if login was successful
    */
   public boolean login()
   {
      boolean loginSuccess = false;
      String statement = "SELECT * FROM people WHERE username= ? AND password= ? ;";
      
      try{
         dbConn.connect();
         ArrayList<ArrayList<String>> rs = dbConn.getData(statement,userName,password);
         if(rs != null){
            dbConn.close();
            userID = Integer.parseInt(rs.get(0).get(0));
            userName = rs.get(0).get(2);
            password = rs.get(0).get(3);
            fName = rs.get(0).get(5);
            lName = rs.get(0).get(6);
            email = rs.get(0).get(4);
            phone = rs.get(0).get(7);
            office = rs.get(0).get(8);
            userType = rs.get(0).get(1);
            loginSuccess = true;
         }
         else{
            dbConn.close();
            loginSuccess = false;
         }
      }
      catch(DLException dle){
         loginSuccess = false;
         System.out.println("*** Error: " + dle.getMessage() + " ***\n");
         dle.printStackTrace();
      }
      return loginSuccess;
   }
   
   /**
    * Method that retrieves the project IDs associated with this user
    * @return boolean that states if this user has any projects associated with them
    */
   public boolean getProjectIds(){
      String query = "SELECT pid FROM people_project JOIN people USING(uid) WHERE uid= ?;";
      boolean idsFound = false;
      
      try{
         dbConn.connect();
         ArrayList<ArrayList<String>> rs = dbConn.getData(query,String.valueOf(userID));
         if(rs != null){
            dbConn.close();
            for(int i = 0; i<rs.size(); i++){
               projectIds.add(rs.get(i).get(0));
            }
            idsFound = true;
         }
         else{
            dbConn.close();
            idsFound = false;
         }
      }
      catch(DLException dle){
         idsFound = false;
         System.out.println("*** Error: " + dle.getMessage() + " ***\n");
         dle.printStackTrace();
      }
      return idsFound;
   }
   
   /**
    * Returns edit boolean that determines if the user can modify records
    * @return canEdit boolean that determines if this user has permissions to modify data
    */
   public String checkUserType()
   {
      return userType;
   }
   
}