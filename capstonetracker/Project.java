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
    private connectDB dbConn;
    private int userID;

    /**
    * Default constructor
    */
    public Project()
    {
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
        dbConn = new connectDB();
    }

    public Project(int _projectID)
    {
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
    public Project(int _projectID, String _projectType, String _projectName, String _projectDescription, String _startDate, String _endDate)
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
    * After login, check what project did the user have
    */
    public ArrayList<ArrayList<String>> getUserProject(int userID)
    {
        ArrayList<ArrayList<String>> userProjectList = new ArrayList<>();
        String statement = "SELECT * FROM people_project WHERE uid = ?;";
        try
        {
            userProjectList = dbConn.getData(statement, Integer.toString(userID));
        }
        catch(DLException dle)
        {
            System.out.println("*** Error: " + dle.getMessage() + " ***\n");
            dle.printStackTrace();
        }
        return userProjectList;
    }

    /**
    * Fetch project data from database.
    */
    public boolean checkProject() {
        String statement = "SELECT type, name, description, start_term, expected_end_date, plagiarism_score, grade FROM project WHERE pid = ?;";
        boolean check = false;
        try {
            dbConn.connect();
            ArrayList<ArrayList<String>> data = dbConn.getData(statement, Integer.toString(getProjectID()));
            dbConn.close();
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
    * getProjectID method return projectID
    */
    public int getProjectID()
    {
        return projectID;
    }

    /**
    * getProjectType method return projectType
    */
    public String getProjectType()
    {
        return projectType;
    }

    /**
    * getProjectName method return projectName
    */
    public String getProjectName()
    {
        return projectName;
    }

    /**
    * getProjectDescription method return projectDescription
    */
    public String getProjectDescription()
    {
        return projectDescription;
    }

    /**
    * getStartDate method return startdate
    */
    public String getStartDate()
    {
        return startDate;
    }

    /**
    * getEndDate method return enddate
    */
    public String getEndDate()
    {
        return startDate;
    }

    /**
    * getPlagiarismScore method return plagiarismScore
    */
    public int getPlagiarismScore()
    {
        return plagiarismScore;
    }

    /**
    * getScore method return score
    */
    public int getGrade()
    {
        return grade;
    }


    /**
    * addNewProject allow user to insert new project to database
    */
    public void addNewProject(BLUser user)
    {
        String statement = "INSERT INTO project VALUES(?,?,?,?,?);";
        try
        {
            String getMaxProjectId = "SELECT MAX(pid) FROM project;";
            ArrayList<ArrayList<String>> rs = dbConn.getData(getMaxProjectId);
            projectID = Integer.parseInt(rs.get(0).get(0)) + 1;

            boolean add = dbConn.setData(statement, String.valueOf(projectID), projectName, projectDescription, startDate, endDate);
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
            data = dbConn.getData(statement, Integer.toString(projectID));
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
    * updateProjectInfo allow user to change project detail
    * BUT student are not allow to change plagirism score and grade
    */
    public void updateProjectInfo(String partOfChange, BLUser user)
    {
        String userType = user.getUserType();
        if(userType.equalsIgnoreCase("Student") && partOfChange == "plagiarism_score")
        {
            System.out.println("Student not allow to change plagirism score");
        }
        else if(userType.equalsIgnoreCase("Student") && partOfChange == "grade")
        {
            System.out.println("Student not allow to change grade");
        }
        else
        {
            String statement = "UPDATE projects SET " + partOfChange + " = ? WHERE pid = ?;" ;
            try
            {
                boolean update = dbConn.setData(statement, changeTo, Integer.toString(projectID) );
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
