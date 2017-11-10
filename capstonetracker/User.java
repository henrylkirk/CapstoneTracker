package capstonetracker;

/**
* Class the stores values for users in capstoneProjects DB
* ISTE330 Team Project
* Ryan Sweeney, Henry Kirk, Zhimin Lin
*/

public class User  
{
   private int userID;
   private String userName;
   private String password;
   private String fName;
   private String lName;
   private String email;
   private String userType;
   private boolean canEdit;
   private ConnectDB dbConn;
   
   /**
    * Constructor that sets username and password
    * @param _userName the username for this user
    * @param _password the password for this user
    */
   public User(String _userName, String _password){
      userName = _userName;
      password = _password;
      userId = 0;
      fName = "NULL";
      lName = "NULL";
      email = "NULL";
      userType = "NULL";
      canEdit = false;
      dbConn = new ConnectDB();
   }
   
   /**
    * Constructor that sets all values for user object
    * @param _userName username for this user record
    * @param _password password for this user record
    * @param _fName first name for this user
    * @param _lname last name for this user
    * @param _email email for this user record
    * @param _userType userType for this user record
    */
   public User(String _userName, String _password, String _fName, String _lName, String _email, String _userType)
   {
      userName = _userName;
      password = _password;
      fName = _fName;
      lName = _lName;
      email = _email;
      userType = _userType;
      dbConn = new ConnectDB();
      if(userType.equalsIgnoreCase("Faculty")){
         canEdit = true;
      }else{
         canEdit = false;
      }
   }
   
   /**
    * Method that post new users to db
    * @return posted boolean that determines if insert was successful
    */
   public boolean registerAccount()
   {
      String statement = 
         "INSERT INTO equipment VALUES(?,?,?,?,?,?);";
      boolean posted = false;
      
      try{
         dbConn.connect();
         if(dbConn.setData(statement,userName,password,fName,lName,email,userType)){
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
      String statement = "SELECT * FROM users WHERE username= ? AND password= ? ;";
      
      try{
         dbConn.connect();
         ArrayList<ArrayList<String>> rs = dbConn.getData(statement,userName,password);
         if(rs != null){
            dbConn.close();
            userID = Integer.parseInt(rs.get(0).get(0));
            fName = rs.get(0).get(3);
            lName = rs.get(0).get(4);
            email = rs.get(0).get(5);
            userType = rs.get(0).get(6);
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
    * Returns edit boolean that determines if the user can modify records
    * @return canEdit boolean that determines if this user has permissions to modify data
    */
   public boolean checkPermession()
   {
      return canEdit;
   }
   
}