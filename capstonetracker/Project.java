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
 public class Project extends connectDB
 { 
   public int projectID;
   public String projectName;
   public String projectDescription;
   public String startDate;
   public String endDate;
   public String changeTo;
   public String partOfChange;
   public String userType;
   
   /**
    * Default constructor
    */
   public Project()
   {
      projectID = 0;
      projectName = "NULL";
      projectDescription = "NULL";
      startDate = "NULL";
      endDate =  "NULL";
      changeTo = "NULL";
      partOfChange = "NULL";
      userType = "NULL";
   }
   
   /**
    * Constructor that sets all values for Project object
    * projectID: project ID number
    * projectName: project name
    * projectDesctiption: project desctipition 
    * startDate: the date of the this project start
    * endDate: the duedate for this project
    */
   public Project(int _projectID, String _projectName, String _projectDescription, String _startDate, String _endDate)
   {
      projectID = _projectID;
      projectName = _projectName;
      projectDescription = _projectDescription;
      startDate = _startDate;
      endDate =  _endDate;
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
         myArray = getData(statement, projectID);
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
      catch(Exception e)
      {
         throw new DLException(e,"checkProject() error");
      }
   }
   
   /**
    * Only faculty are allow to use this method, student are not allow
    * faculty able to add new project
    * if there are no data match, show messsage: No data is retrieved
    */
   public void addNewProject()
   {
      if(userType.equalsIgnoreCase("Student"))
      {
         System.out.println("Student not able to add new project");
      }
      else
      {
         ArrayList<ArrayList<String>> myArray = new ArrayList<>();
         String statement = "INSERT INTO project VALUES(?,?,?,?,?);";
         try
         {
            myArray = setData(statement, projectID, projectName, projectDescription, startDate, endDate);
            if (myArray.size() == 0)
            {
               System.out.println("No data is retrieved");
            }
            else
            {
               System.out.println("New Project Added");
            }
         }
         catch(Exception e)
         {
            throw new DLException(e,"addNewProject() error");
         }
      }
   }
   
   /**
    * Only faculty are allow to use this method, student are not allow
    * faculty able to update project information
    * if there are no data match, show messsage: No data is retrieved
    */
   public void updateProjectInfo(String partOfChange)
   {
      if(userType.equalsIgnoreCase("Student"))
      {
         System.out.println("Student not able to update project information");
      }
      else
      {
         ArrayList<ArrayList<String>> myArray = new ArrayList<>();
         String statement = "UPDATE projects SET " + partOfChange + " = ? WHERE pid = ?;" ;
         try
         {
            myArray = setData(statement, changeTo, projectID );
            if (myArray.size() == 0)
            {
               System.out.println("No data is retrieved");
            }
            else
            {
               System.out.println("Data Updated");
            }
         }
         catch(Exception e)
         {
            throw new DLException(e,"updateProjectInfo() error");
         }
       }

   }
      
 }