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
   public String projectName;
   public int projectID;
   public String description;
   
   // Constructor
   public User()
   {
      
   }
   
   // registerAccount method allow user to create new account, it can be either student or faculty
   // Data need: userName, password, fName, lName, email, userType
   public void registerAccount(String userName, String password, String fName, String lName, String email, String userType)
   {
      
   }
   
   // login method allow user login to their account
   // After user login, GUI will display the project information 
   // Data need: userName, password
   public void login(String userName, String password)
   {
      
   }
   
   
   // checkIdentity method also able to check user's identity
   // if faculty: setEditable(true); Facultys are allow to change everything on GUI
	//	if student: setEditable(false), Students are not able to change everything on GUI 
   // Data need: userType
   public void checkIdentity(String userType)
   {
   
   }
   
   // newChange method will save all the change that faculty make on GUI, and update the database
   // And the GUI will always display the newest
   // **May use Arraylist in this method 
   public void newChange()
   {
   
   }
   
   // modifyProject method allow faculty adding more project 
   // Data need: projectID, projectName
   public void modifyProject(String projectID, String projectName)
   {
      
   }
   
   // updateProjectInfo method allow faculty update the project information
   // Data need: projectID, projectName, description
   public void updateProjectInfo(String projectID, String projectName, String description)
   {
      
   }
   
   
}