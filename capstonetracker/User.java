package capstonetracker;

/*
* ISTE330 Team Project
* Ryan Sweeney, Henry Kirk, Zhimin Lin
*/

 
//This class extends to GUI class 
public class User  
{
   public String userName;
   public String password;
   public String fName;
   public String lName;
   public String email;
   public String userType;
   private boolean canEdit;
   
   //Basic default constructor - Just takes username and pass
   public User(String _userName, String _password){
      userName = _userName;
      password = _password;
      fName = "NULL";
      lName = "NULL";
      email = "NULL";
      userType = "NULL";
      canEdit = false;
   }
   
   // Full parameterized Constructor - Info for either existing or current record
   public User(String _userName, String _password, String _fName, String _lName, String _email, String _userType)
   {
      userName = _userName;
      password = _password;
      fName = _fName;
      lName = _lName;
      email = _email;
      userType = _userType;
      if(userType.equalsIgnoreCase("Faculty")){
         canEdit = true;
      }else{
         canEdit = false;
      }
   }
   
   // registerAccount method allow user to create new account, it can be either student or faculty
   public void registerAccount()
   {
      //Prepare statement insert record into Users table
   }
   
   // login method allow user login to their account
   // After user login, GUI will display the project information 
   // Data need: userName, password
   public boolean login(String userName, String password)
   {
      //Select statement on DB, if record found fill remaining varables and return true
      return true;
   }
   
   
   // checkIdentity method also able to check user's identity
   // if faculty: setEditable(true); Facultys are allow to change everything on GUI
	//	if student: setEditable(false), Students are not able to change everything on GUI 
   // Data need: userType
   public boolean checkPermession()
   {
      return canEdit;
   }
   
   // newChange method will save all the change that faculty make on GUI, and update the database
   // And the GUI will always display the newest
   // **May use Arraylist in this method 
   public void newChange()
   {
   
   }
   
}