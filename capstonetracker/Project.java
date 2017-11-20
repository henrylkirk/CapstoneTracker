package capstonetracker;

/**
 * Project class: check data, add new data and update data. 
 * @author Zhimin Lin
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.*;

/**
 * New class named Project
 * Project class extends connectDB class
 */
 public class Project
 { 
   private String projectID;
   private String projectName;
   private String projectType;
   private String projectDescription;
   private String startDate;
   private String endDate;
   private int plagiarismScore;
   private String changeTo;
   private String partOfChange;
   private String status;
   private ArrayList<String> userIds;
   private connectDB dbConn;
   
   /**
    * Default constructor
    */
   public Project()
   {
      projectID = "NULL";
      projectName = "NULL";
      String projectType = "NULL";
      projectDescription = "NULL";
      startDate = "NULL";
      endDate =  "NULL";
      plagiarismScore = 0;
      changeTo = "NULL";
      partOfChange = "NULL";
      userIds = null;
      dbConn = new connectDB();
   }
   
   public Project(String _projectID)
   {
      projectID = _projectID;
      projectName = "NULL";
      projectType = "NULL";
      projectDescription = "NULL";
      startDate = "NULL";
      endDate =  "NULL";
      plagiarismScore = 0;
      changeTo = "NULL";
      partOfChange = "NULL";
      userIds = null;
      dbConn = new connectDB();
   }
   
   /**
    * Constructor that sets all values for Project object
    * projectID: project ID number
    * projectName: project name
    * projectDesctiption: project desctipition 
    * startDate: the date of the this project start
    * endDate: the duedate for this project
    */
   public Project(String _projectID, String _projectType, String _projectName, String _projectDescription, String _startDate, String _endDate)
   {
      projectID = _projectID;
      projectType = _projectType;
      projectName = _projectName;
      projectDescription = _projectDescription;
      startDate = _startDate;
      endDate =  _endDate;
      dbConn = new connectDB();
   } 
   
   /**
    * Both student and faculty able to check is the project exists or not.
    * Both student and faculty able to see project information, include id, name, description, startdate and enddate
    * if there are no data match, show messsage: No data is retrieved
    */
   public boolean checkProject()
   {
      ArrayList<ArrayList<String>> myArray = new ArrayList<>();
      String statement = "SELECT * FROM projects WHERE pid = ?;";
      boolean check = false;
      try
      {
         myArray = dbConn.getData(statement, projectID);
         if (myArray.size() == 0)
         {
            check = false;
         }
         else
         {
            check = true;
            projectID = myArray.get(0).get(0);
            projectType = myArray.get(0).get(1);
            projectName = myArray.get(0).get(2);
            projectDescription = myArray.get(0).get(3);
            startDate = myArray.get(0).get(4);
            endDate = myArray.get(0).get(5);
            plagiarismScore =  Integer.parseInt(myArray.get(0).get(6));
         }
      }
      catch(DLException dle)
      {
         System.out.println("*** Error: " + dle.getMessage() + " ***\n");
         dle.printStackTrace();
      }
      return check;
   }
   
   /**
    * Only faculty are allow to use this method, student are not allow
    * faculty able to add new project
    */
   public void addNewProject(BLUser user)
   {
      String userType = user.checkUserType();
      if(userType.equalsIgnoreCase("Student"))
      {
         System.out.println("Student not able to add new project");
      }
      else
      {
         String statement = "INSERT INTO project VALUES(?,?,?,?,?);";
         try
         {
            boolean add = dbConn.setData(statement, projectID, projectName, projectDescription, startDate, endDate);
            if (add)
            {
               System.out.println("New Project Added");
            }
            else
            {
               System.out.println("New Project Added Failed");
            }
         }
         catch(DLException dle)
         {
            System.out.println("*** Error: " + dle.getMessage() + " ***\n");
            dle.printStackTrace();
         }
      }
   }

   /**
    * Returns an ArrayList of users associated with this project.
    * @return ArrayList
    */
   // public ArrayList<User> getProjectUsers(){
   //    return users;
   // }

   /**
    *
    */
   public boolean addProjectUser(String username, String role){
      // check if this username exists in the database
      boolean check = false;
      String statement = "SELECT * FROM users WHERE username = ?";
      ArrayList<ArrayList<String>> data = new ArrayList<>();

      try {
         data = dbConn.getData(statement, projectID);
         if (data.size() == 0) {
            check = false;
         } else {

         }
         // if so, add user to ArrayList
      } catch(DLException dle){
         System.out.println("*** Error: " + dle.getMessage() + " ***\n");
         dle.printStackTrace();
      }

      return check;
   }
   
   /**
    * Only faculty are allow to use this method, student are not allow
    * faculty able to update project information
    */
   public void updateProjectInfo(String partOfChange, BLUser user)
   {
      String userType = user.checkUserType();
      if(userType.equalsIgnoreCase("Student"))
      {
         System.out.println("Student not able to update project information");
      }
      else
      {
         String statement = "UPDATE projects SET " + partOfChange + " = ? WHERE pid = ?;" ;
         try
         {
            boolean update = dbConn.setData(statement, changeTo, projectID );
            if (update)
            {
               System.out.println("Data Updated");
            }
            else
            {
               System.out.println("Data Update Failed");
            }
         }
         catch(DLException dle)
         {
            System.out.println("*** Error: " + dle.getMessage() + " ***\n");
            dle.printStackTrace();
         }
       }

   }
      
 }