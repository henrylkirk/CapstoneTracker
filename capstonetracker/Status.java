package capstonetracker;

import java.sql.*;
import java.util.ArrayList;
import java.util.*;

/**
 * Status class for project status and related Info
 * @author Ryan Sweeney
 */
public class Status{

   private int statusId;
   private String statusName;
   private String statusDesc;
   private String lastDateModified;
   private String comment;
   private ConnectDB dbConn;

   /**
    * Constructor for a Status Object
    * @param _statusId The status Id for this status object
    * @param _dateModified The date when this status was assigned to a project
    * @param _comment A comment about the status that was added
    */
   public Status(int _statusId, String _dateModified, String _comment){
      statusId = _statusId;
      lastDateModified = _dateModified;
      comment = _comment;
      statusName = "NULL";
      statusDesc = "NULL";
      dbConn = new ConnectDB();
   }
   
   /**
    * Method that Retrieves info for the status object
    * @return boolean that determines if the check was successful
    */
   public boolean checkStatusInfo(){
      String query = "SELECT name, description FROM status WHERE sid = ? ;";
      boolean gotStatus = false;
      try{
         dbConn.connect();
         ArrayList<ArrayList<String>> rs = dbConn.getData(query,String.valueOf(statusId));
         if(rs != null){
            dbConn.close();
            statusName = rs.get(0).get(0);
            statusDesc = rs.get(0).get(1);
            gotStatus = true;
         }else{
            dbConn.close();
            gotStatus = false;
         }
      }
      catch(DLException dle){
         System.out.println("*** Error: " + dle.getMessage() + " ***\n");
         dle.printStackTrace();
      }
      return gotStatus;
   }

   /**
    * Method that updates the status for a project
    * @param _pid The projectID for the project being updated
    * @return boolean that determines if the operation was successful
    */
   public boolean updateProjectStatus(String _pid){
      String query = "INSERT INTO project_status(sid,pid,last_modified,comment) VALUES(?,?,?,?);";
      String project = _pid;
      boolean updatePushed = false;
      try{
         dbConn.connect();
         if(dbConn.setData(query,String.valueOf(statusId),project,lastDateModified,comment)){
            updatePushed = true;
         }else{
            updatePushed = false;
         }
      }
      catch(DLException dle){
         System.out.println("*** Error: " + dle.getMessage() + " ***\n");
         dle.printStackTrace();
      }
      return updatePushed;
   }

   /**
    * Mutator to modify statusID of this instance
    * @param _id the status id for this object
    */
   public void setStatusId(int _id){
      statusId = _id;
   };

   /**
    * Mutator to modify statusName
    * @param _name the name of this status
    */
   public void setStatusName(String _name){
      statusName = _name;
   };

   /**
    * Mutator to modify the Description for the status
    * @param _desc The description of this status
    */
   public void setStatusDescription(String _desc){
      statusDesc = _desc;
   };

   /**
    * Mutator to modify the date when this status was assigned to a project
    * @param _date The date the status was assigned to a project
    */
   public void setDateModified(String _date){
      lastDateModified = _date;
   };

   /**
    * Mutator to modify the comment appended when updating the status for a project
    * @param _comment The comment to add 
    */
   public void setComment(String _comment){
      comment = _comment;
   };

   /**
    * Method that returns the statusID
    * @return the statusID
    */
   public int getStatusId(){
      return statusId;
   }

   /**
    * Method that returns the status Name
    * @return the status name
    */
   public String getStatusName(){
      return statusName;
   }

   /**
    * Method that returns the status description
    * @return the status description
    */
   public String getStatusDescription(){
      return statusDesc;
   }

   /**
    * Method that returns the date the status was assigned to a project
    * @return the date when the status was assigned to a project
    */
   public String getLastDateModified(){
      return lastDateModified;
   }

   /**
    * Method that returns the comment the was added alongside the status for a project
    * @return the comment added with the status for a project
    */
   public String getComment(){
      return comment;
   }

}
