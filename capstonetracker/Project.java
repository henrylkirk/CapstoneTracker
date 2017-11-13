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
   private String projectDescription;
   private String startDate;
   private String endDate;
   private String changeTo;
   private String partOfChange;
   private connectDB dbConn;
   
   /**
    * Default constructor
    */
   public Project()
   {
      projectID = "NULL";
      projectName = "NULL";
      projectDescription = "NULL";
      startDate = "NULL";
      endDate =  "NULL";
      changeTo = "NULL";
      partOfChange = "NULL";
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
   public Project(String _projectID, String _projectName, String _projectDescription, String _startDate, String _endDate)
   {
      projectID = _projectID;
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
   public void checkProject()
   {
      ArrayList<ArrayList<String>> myArray = new ArrayList<>();
      String statement = "SELECT * FROM projects WHERE pid = ?;";
      try
      {
         myArray = dbConn.getData(statement, projectID);
         if (myArray.size() == 0)
         {
            System.out.println("No data is retrieved");
         }
         else
         {
             for(int i = 0; i < myArray.size(); i++)
             {
                for(int j = 0; j < myArray.get(i).size(); j++)
                {
                   System.out.println(myArray.get(i).get(j) + " ");
                }
               System.out.println();
             }
         }
      }
      catch(DLException dle)
      {
         System.out.println("*** Error: " + dle.getMessage() + " ***\n");
         dle.printStackTrace();
      }
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