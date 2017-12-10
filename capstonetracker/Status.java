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

   public Status(int _statusId, String _dateModified, String _comment){
      statusId = _statusId;
      lastDateModified = _dateModified;
      comment = _comment;
      statusName = "NULL";
      statusDesc = "NULL";
      dbConn = new ConnectDB();
   }

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


   public void setStatusId(int _id){
      statusId = _id;
   };

   public void setStatusName(String _name){
      statusName = _name;
   };

   public void setStatusDescription(String _desc){
      statusDesc = _desc;
   };

   public void setDateModified(String _date){
      lastDateModified = _date;
   };

   public void setComment(String _comment){
      comment = _comment;
   };

   public int getStatusId(){
      return statusId;
   }

   public String getStatusName(){
      return statusName;
   }

   public String getStatusDescription(){
      return statusDesc;
   }

   public String getLastDateModified(){
      return lastDateModified;
   }

   public String getComment(){
      return comment;
   }

}
