package capstonetracker;
/**
 * Business layer Project class
 * @author Ryan Sweeney
 * Revised: 11/13/17
 */
public class BLProject extends Project {


   /**
    * Parameterized Constructor for Project Layer User
    * 
    */
   public BLProject(int _projectID){
      super(_projectID);
   }
   
   public BLProject(int _projectID, String _projectType, String _projectName, String _projectDescription, String _startDate, String _endDate){
      super(_projectID, _projectType, _projectName, _projectDescription, _startDate,_endDate);
   }
   
}