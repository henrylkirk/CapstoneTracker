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
public class Project {
    private int projectID;
    private String projectName;
    private String projectType;
    private String projectDescription;
    private String startDate;
    private String endDate;
    private int plagiarismScore;
    private int grade;
    private String changeTo;
    private String partOfChange;
    private String status;
    private ArrayList<String> userIds;
    private ConnectDB dbConn;
    private int userID;

    /**
    * Default constructor
    */
    public Project() {
        projectID = 0;
        projectName = "NULL";
        String projectType = "NULL";
        projectDescription = "NULL";
        startDate = "NULL";
        endDate =  "NULL";
        plagiarismScore = 0;
        grade = 0;
        changeTo = "NULL";
        partOfChange = "NULL";
        userIds = null;
        dbConn = new ConnectDB();
    }

    public Project(int _projectID) {
        projectID = _projectID;
        projectName = "NULL";
        projectType = "NULL";
        projectDescription = "NULL";
        startDate = "NULL";
        endDate =  "NULL";
        plagiarismScore = 0;
        grade = 0;
        changeTo = "NULL";
        partOfChange = "NULL";
        userIds = null;
        dbConn = new ConnectDB();
    }

    /**
    * Constructor that sets all values for Project object
    * projectID: project ID number
    * projectName: project name
    * projectDesctiption: project desctipition
    * startDate: the date of the this project start
    * endDate: the duedate for this project
    */
    public Project(int _projectID, String _projectType, String _projectName, String _projectDescription, String _startDate, String _endDate) {
        projectID = _projectID;
        projectType = _projectType;
        projectName = _projectName;
        projectDescription = _projectDescription;
        startDate = _startDate;
        endDate =  _endDate;
        dbConn = new ConnectDB();
    }

    /**
    * Fetch project data from database.
    */
    public boolean checkProject() {
        String statement = "SELECT type, name, description, start_term, expected_end_date, plagiarism_score, grade FROM project WHERE pid = ?";
        boolean check = false;
        try {
            dbConn.connect();
            ArrayList<ArrayList<String>> data = dbConn.getData(statement, Integer.toString(projectID));
            if (data != null) {
                check = true;
                projectType = data.get(0).get(0);
                projectName = data.get(0).get(1);
                projectDescription = data.get(0).get(2);
                startDate = data.get(0).get(3);
                endDate = data.get(0).get(4);
                plagiarismScore = Integer.parseInt(data.get(0).get(5));
                grade = Integer.parseInt(data.get(0).get(6));
                
            }
        } catch(DLException dle) {
            System.out.println("*** Error: " + dle.getMessage() + " ***\n");
            dle.printStackTrace();
        }
        return check;
    }

    /**
    * For a given project id, get the user's role on that project.
    */
    public String getRole(int userID) {
        String role = "Grad"; // default to student in case of error
        String statement = "SELECT role FROM people_project WHERE uid = ? AND pid = ?";
        System.out.println("User id: "+Integer.toString(userID));
        System.out.println("Project id: "+Integer.toString(getProjectID()));
        try {
            dbConn.connect();
            ArrayList<ArrayList<String>> rs = dbConn.getData(statement, Integer.toString(userID), Integer.toString(getProjectID()));
            dbConn.close();
            if(rs != null){
                role = rs.get(0).get(0);
            }
        } catch(DLException dle) {
            System.out.println("*** Error: " + dle.getMessage() + " ***\n");
            dle.printStackTrace();
        }
        System.out.println(role);
        return role;
    }
    
   /**
    * get the status for the project
    * return 2d arraylist includes all statused for that project
    */
    public ArrayList<ArrayList<String>> getStatus()
    {
      String statement = "select * from project_status where pid = ?";
      ArrayList<ArrayList<String>> statusArray = new ArrayList<ArrayList<String>>();
      try
      {
         dbConn.connect();
         statusArray = dbConn.getData(statement, Integer.toString(getProjectID()));
      }
      catch(DLException dle) 
      {
         System.out.println("*** Error: " + dle.getMessage() + " ***\n");
         dle.printStackTrace();
      }
      return statusArray;
    }
    

    public void setProjectType(String _projectType){
        projectType = _projectType;
    }

    public void setProjectName(String _projectName){
        projectName = _projectName;
    }

    public void setProjectDescription(String _projectDescription){
        projectDescription = _projectDescription;
    }

    public void setStartDate(String _startDate){
        startDate = _startDate;
    }

    public void setEndDate(String _endDate){
        endDate = _endDate;
    }

    public void setPlagiarismScore(int _plagiarismScore){
        plagiarismScore = _plagiarismScore;
    }

    public void setGrade(int _grade){
        grade = _grade;
    }

    public int getProjectID(){
        return projectID;
    }

    public String getProjectType(){
        return projectType;
    }

    public String getProjectName(){
        return projectName;
    }

    public String getProjectDescription(){
        return projectDescription;
    }

    public String getStartDate(){
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getPlagiarismScore(){
        return plagiarismScore;
    }

    public int getGrade() {
        return grade;
    }


    /**
    * addNewProject allow user to insert new project to database
    */
    public void addNewProject() {
        String statement = "INSERT INTO project VALUES(?,?,?,?,?,?,?,?);";
        try {
            dbConn.connect();
            String getMaxProjectId = "SELECT MAX(pid) FROM project;";
            ArrayList<ArrayList<String>> rs = dbConn.getData(getMaxProjectId);
            int newProjectID = Integer.parseInt(rs.get(0).get(0)) + 1;

            boolean add = dbConn.setData(statement, String.valueOf(newProjectID), projectName, projectType,projectDescription, startDate, endDate, String.valueOf(plagiarismScore), String.valueOf(grade));
            if (add) {
                System.out.println("New Project Added");
            } else {
                System.out.println("New Project Added Failed");
            }
        } catch(DLException dle) {
            System.out.println("*** Error: " + dle.getMessage() + " ***\n");
            dle.printStackTrace();
        }
    }

    /**
    *
    */
    public boolean addProjectUser(String username, String role){
        // check if this username exists in the database
        boolean check = false;
        String statement = "SELECT * FROM people WHERE username = ?";
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        try {
            dbConn.connect();
            data = dbConn.getData(statement, Integer.toString(projectID));
            if (data == null) {
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
    * updateProjectInfo allow user to change project detail
    * BUT student are not allow to change plagirism score and grade
    */
    public void updateProjectInfo(String role) {
        if(role.equalsIgnoreCase("Grad")) {
            String statement = "UPDATE project SET name = ?, type = ?, description = ?, start_term = ?, expected_end_date = ? WHERE pid = ?";
            try {
                dbConn.connect();
                boolean update = dbConn.setData(statement, projectName, projectType, projectDescription, startDate, endDate, Integer.toString(projectID) );
                if (update) {
                    System.out.println("Data Updated");
                }
                else {
                    System.out.println("Data Update Failed");
                    System.out.println("NOTE: student not able to change the grade");
                }
            }
            catch(DLException dle) {
                System.out.println("*** Error: " + dle.getMessage() + " ***\n");
                dle.printStackTrace();
            }

        } else {
            String statement = "UPDATE project SET name = ?,  type = ?, description = ?, start_term = ?, expected_end_date = ?, plagiarism_score = ?, grade = ? WHERE pid = ?;" ;
            try {
                dbConn.connect();
                boolean update = dbConn.setData(statement, projectName, projectType, projectDescription, startDate, endDate,String.valueOf(plagiarismScore),String.valueOf(grade), String.valueOf(projectID) );
                if (update) {
                    System.out.println("Data Updated");
                } else {
                    System.out.println("Data Update Failed");
                }
            } catch(DLException dle) {
                System.out.println("*** Error: " + dle.getMessage() + " ***\n");
                dle.printStackTrace();
            }
        }
    }

    /**
     * Get all users associated with this project.
     * @return 2D ArrayList of strings in format: username, role, uid
     */
    public ArrayList<ArrayList<String>> getUsers(){
        ArrayList<ArrayList<String>> users = new ArrayList<ArrayList<String>>();
        String query = "SELECT people.username, people_project.role, people.uid FROM people, people_project, project WHERE people_project.pid = project.pid AND people.uid = people_project.uid AND project.pid = ?";
        try {
            dbConn.connect();
            users = dbConn.getData(query, Integer.toString(getProjectID()));
        } catch(DLException dle) {
            System.out.println("*** Error: " + dle.getMessage() + " ***\n");
            dle.printStackTrace();
        }
        return users;
    }

   /*
    * This method allow user to add a new user to the project
    * 1. Check is the current user working on the project by using projectID
    * 2. If No, the current user not able to add new user to the project
    * 4. if Yes, check the new user's role, it can't be student
    * 5. if the role is not student, check the uid of the new user by using the username
    * 5. Insert to table.
    */
    public void addUser(BLUser user, String username, int pid) {
      int userID = user.getUserId();
      boolean checkUserProject = false;
      String statement = "SELECT * FROM people_project WHERE uid = ? AND pid = ?;";
      ArrayList<ArrayList<String>> data = new ArrayList<>();
      try {
         dbConn.connect();
         data = dbConn.getData(statement,Integer.toString(userID),Integer.toString(pid));
         if (data == null) {
            checkUserProject = false;
            System.out.println("you're not able to add user, unless you working for the project");
         } else {
            checkUserProject = true;
            String statement2 = "select uid, type from people where username = ?;";
            ArrayList<ArrayList<String>> data2 = new ArrayList<>();
            try {
               dbConn.connect();
               data2 = dbConn.getData(statement2,username);
               int newUserID = Integer.parseInt(data2.get(0).get(0));
               String newUserType = data2.get(0).get(1);
               if(newUserType.equalsIgnoreCase("Grad")) {
                  System.out.println("Can't add student");
               } else {
                  System.out.println("ok,add prof");
                  String statement3 = "insert into people_project values(?,?,?);";
                  try {
                     dbConn.connect();
                     dbConn.setData(statement3,Integer.toString(newUserID), Integer.toString(pid), newUserType);
                  } catch(DLException dle) {
                     System.out.println("*** Error: " + dle.getMessage() + " ***\n");
                     dle.printStackTrace();
                  }

               }
            } catch(DLException dle) {
               System.out.println("*** Error: " + dle.getMessage() + " ***\n");
               dle.printStackTrace();
            }
         }
      } catch(DLException dle) {
         System.out.println("*** Error: " + dle.getMessage() + " ***\n");
         dle.printStackTrace();
      }
    }
   
    
}
