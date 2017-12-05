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
        String statement = "SELECT uid, type, userName, password, fName, lName, email, phone, officelocation FROM people WHERE username= ? AND password= ? ;";

        try{
            dbConn.connect();
            ArrayList<ArrayList<String>> rs = dbConn.getData(statement,userName,password);
            if(rs != null){
                dbConn.close();
                userID = Integer.parseInt(rs.get(0).get(0));
                userType = rs.get(0).get(1);
                userName = rs.get(0).get(2);
                password = rs.get(0).get(3);
                fName = rs.get(0).get(5);
                lName = rs.get(0).get(6);
                email = rs.get(0).get(4);
                phone = rs.get(0).get(7);
                office = rs.get(0).get(8);
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
    public ArrayList<Project> getProjects() {
        String query = "SELECT pid FROM project JOIN people_project using(pid) join people using(uid) WHERE uid = ?;";
        ArrayList<Project> projects = new ArrayList<Project>();
        ArrayList<ArrayList<String>> rs = new ArrayList<ArrayList<String>>();
        try {
            dbConn.connect();
            rs = dbConn.getData(query,Integer.toString(userID));
            dbConn.close();
            if(rs != null){
                for(int i = 0; i<rs.size(); i++){
                    int newPID = Integer.parseInt(rs.get(i).get(0));
                    Project newProject = new Project(newPID);
                    if(newProject.checkProject()){
                        projects.add(newProject);
                    }
                }
            }
        } catch(DLException dle) {
            System.out.println("*** Error: " + dle.getMessage() + " ***\n");
            dle.printStackTrace();
        }
        return projects;
    }

    /**
    * Mutator to set the ID for this user
    * @param _userId ID number to assign this user
    */
    public void setUserId(int  _userId){
        userID = _userId;
    }

    /**
    * Method to return userId
    * @return the userId for this user
    */
    public int getUserId()
    {
        return userID;
    }

    /**
    * Mutator to set the user type for this user
    * @param _userType the user type for this user
    */
    public void setUserType(String _userType){
        userType = _userType;
    }

    /**
    * Method that returns the usertype assigned to this user
    * @return the user type of this user
    */
    public String getUserType()
    {
        return userType;
    }

    /**
    * Mutator that sets the username used to acces the account of this user
    * @param _userName the username for this user's account
    */
    public void setUsername(String _userName){
        userName = _userName;
    }

    /**
    *  Method that returns the username assigned to this user
    * @return the username for this user
    */
    public String getUsername()
    {
        return userName;
    }

    /**
    * Mutator tthat sets a new password for this user, saving it as a hashed string value
    * @param _passString the base string value used to set the new password
    */
    public void setPassword(String _passString){
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(_passString.getBytes());
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
    * Method to retrieve the hashed string for this user's password
    * @return the hashed string password for this user
    */
    public String getPassword()
    {
        return password;
    }

    /**
    * Mutator to set the email address for this user
    * @param _email string that outlines email address for this user
    */
    public void setEmail(String _email){
        email = _email;
    }

    /**
    * Method to return email address for this user
    * @return The email address for this user
    */
    public String getEmail()
    {
        return email;
    }

    /**
    * Mutator to set the user's first name
    * @param _fName the first name for this user
    */
    public void setFirstName(String _fName){
        fName = _fName;
    }

    /**
    * Method that returns the first name of this user
    * @return The user's first name
    */
    public String getFirstName()
    {
        return fName;
    }

    /**
    * Mutator that sets the user's last name
    * @param _lName The last name for this user
    */
    public void setLastName(String _lName){
        lName = _lName;
    }

    /**
    * Method that returns the last name for this user
    * @return the last name of this user
    */
    public String getLastName()
    {
        return lName;
    }

    /**
    * Mutator that sets the phone number for this user; saved as a string value
    * @param _phone phone number for this user
    */
    public void setPhone(String _phone){
        phone = _phone;
    }

    /**
    * Method that returns the phone number assigned to this user
    * @return The phone number of this user
    */
    public String getPhone()
    {
        return phone;
    }

    /**
    * Mutator to set the office number/location for this user
    * @param _office the office number/location for this user
    */
    public void setOffice(String _office){
        office = _office;
    }

    /**
    * Method that returns the office number/location for this user
    * @return the office number/location for this user
    */
    public String getOffice()
    {
        return office;
    }

    public static void main(String[] args){
        User user = new User("ab1234","password");
        user.login();
        ArrayList<Project> projects = user.getProjects();
        System.out.println(projects.get(0).getProjectType());
    }
}
