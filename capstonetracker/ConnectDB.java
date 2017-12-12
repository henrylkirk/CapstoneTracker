package capstonetracker;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class object used to connect to a MySQL Database
 * Last Revised: 11/06/17
 */

public class ConnectDB {

   private String uri; //Location and DB to use
   private String driver; //Driver to use
   private String user; //Username to access DB
   private String pass; //Password to access DB
   private Connection conn; //Connection object
   private int numOfFields; //number of fields recovered when executing a query
   private ArrayList<ArrayList<String>> resultsList;//2d ArrayList to hold results from Queries
   private int [] columnWidths; //int array the stores the max widths for the columns for formatting purposes
   private boolean noRollback; //boolean that determines whether an transaction was rolled back

   /**
    * Default constructor with default options to connect to the database
    */
   public ConnectDB(){
      uri = "jdbc:mysql://localhost/mydb?autoReconnect=true&useSSL=false";
      driver = "com.mysql.jdbc.Driver";
      user = "root";
      pass = "student";
   }

   /**
    * Param constructor that sets specific username and password to access the database; instantiates defaults for uri and driver
    * @param _user sets value for the username
    * @param _pass sets value for the password
    */
   public ConnectDB(String _user, String _pass){
      user = _user;
      pass = _pass;
      uri = "jdbc:mysql://localhost/capstoneprojects?autoReconnect=true&useSSL=false";
      driver = "com.mysql.jdbc.Driver";
   }

   /**
    * Method used to connect to MySQL Database
    * @exception DLException catches exceptions and send info to log file
    * @return isConn boolean that determines whether or not connection has been achieved
    */
   public boolean connect() throws DLException {
      boolean isConn = false;
      try{
         conn = DriverManager.getConnection(uri,user,pass);
         isConn = true;
      }
      catch(SQLException sqle){
         isConn = false;
         throw new DLException(sqle,"SQL Exception caught at connectDB.connect()","Username: " + user,"Password: " + pass,
                                "URI: " + uri,"Driver: " + driver);
      }
      catch(Exception e){
         isConn = false;
         throw new DLException(e,"Misc Exception caught at connectDB.connect()");
      }
      return isConn;
   }

   /**
    * Method used to close connection to the MySQL Database
    * @exception DLException catches exceptions and sends info to log file
    * @return isClosed boolean that determines if the connection has been closed successfully
    */
   public boolean close() throws DLException {
      boolean isClosed = false;
      try{
         conn.close();
         isClosed = true;
      }
      catch(SQLException sqle){
         isClosed = false;
         throw new DLException(sqle,"SQL Exception caught at connectDB.close()");
      }
      catch(Exception e){
         isClosed = false;
         throw new DLException(e,"Misc Exception caught at connectDB.close()");
      }
      return isClosed;
   }

   /**
    * Method that creates a prepare query statement using a string and array of parameters
    * @param _query The string object used to generate the query
    * @param _params String array containing the variables to be binded to the statement
    * @exception DLException throw when any type of exception is caught and writes info to a log file
    * @return a Prepared Query statement with binded variables
    */
   private PreparedStatement prepare(String _query, String [] _params) throws DLException  {
      String query = _query;
      String [] params = _params;
      PreparedStatement prep = null;
      try{
         prep = conn.prepareStatement(query);
         for(int i=0; i<params.length; i++){
            if(params[i] != null){
               prep.setObject(i+1,params[i]);
            }
            else{
               prep.setNull(i+1,Types.OTHER);
            }
         }
      }
      catch(SQLException sqle){
         throw new DLException(sqle,"SQL Exception caught at connectDB.prepare()",
                                "Prepared Statement: " + query,"Parameters: " + Arrays.toString(params));
      }
      catch(Exception e){
         throw new DLException(e,"Misc Exception caught at connectDB.prepare()");
      }
      return prep;
   }

   /**
    * Method that preforms SQL UPDATES, DELETES and INSERTS using a prepared statement
    * @param _statement String object representing the prepared statement to be used
    * @param _params String array of variables to be binded to the prepared statement
    * @exception DLException throw when any type of exception is caught and write info to a log file
    * @return integer value representing the result count
    */
   private int executeStmt(String _statement, String [] _params) throws DLException {
      String statement = _statement;
      String [] params = _params;
      int resultCount = 0;

      try{
         PreparedStatement prepstmt = this.prepare(statement,params);
         resultCount = prepstmt.executeUpdate();
      }
      catch(SQLException sqle){
         resultsList = null;
         throw new DLException(sqle,"SQL Exception caught at connectDB.executeStmt()","Statement: " + statement
                                ,"Parameters: " +Arrays.toString(params));
      }
      catch(Exception e){
         resultsList = null;
         throw new DLException(e,"Misc Excpetion caught at connectDB.executeStmt()");
      }
      return resultCount;
   }

   /**
    * Method to retrieve data from the MySQL Database using a prepared statement
    * @param statement sets string to be used for the SELECT statement
    * @param _params series of string value that represent the parameters to be binded to the prepared statement
    * @exception DLException catches exceptions and sends info to log file
    * @return results 2d ArrayList of values aquirred when executing the SELECT statement
    */
   public ArrayList<ArrayList<String>> getData(String statement, String... _params) throws DLException {
      String query = statement;//String used for the SELECT statement
      String [] params = _params;

      try{
         PreparedStatement stmt = this.prepare(query,params);
         ResultSet resultSet = stmt.executeQuery();
         ResultSetMetaData metaData= resultSet.getMetaData();
         this.setResultingData(resultSet, metaData);
      }
      catch(SQLException sqle){
         resultsList = null;
         throw new DLException(sqle,"SQL Exception caught at connectDB.getData(statement,includeHeader)","Query used: "
                               + query,"Parameters Passed: " + Arrays.toString(params));
      }
      catch(Exception e){
         resultsList = null;
         throw new DLException(e,"Misc Exception caught at connectDB.getData()");
      }
      return resultsList;
   }

   /**
    * Method to perform UPDATE, INSERT and DELETE queries on the MySQL Database using a prepared statement
    * @param statement sets string to be used for the query
    * @param _params series of string value that represent the parameters to be binded to the prepared statement
    * @exception DLException catches exceptions and sends info to log file
    * @return success boolean that determines in the query ran successfully
    */
   public boolean setData(String statement, String... _params) throws DLException {
      String query = statement;
      String [] params = _params;
      boolean success = false;
      int resultCount = 0;

      try{
         resultCount = this.executeStmt(query,params);
         if(resultCount > 0){success = true;}
         else{success = false;}
      }
      catch(Exception e){
         success = false;
         throw new DLException(e,"Misc Exception caught at connectDB.setData(statement,_params)");
      }
      return success;
   }

   /**
    * Private method that initiates and sets to data for the # of fields, columnWidths array, and populates the ResultsList
    * @param _resultSet The ResultSet object the was generated during the last executed query
    * @param _metaData The MetaData generated from the accompanying ResultSet object
    * @exception DLException catches exceptions and sends info to log file
    */
   private void setResultingData(ResultSet _resultSet, ResultSetMetaData _metaData) throws DLException {
      ResultSet rs = _resultSet;
      ResultSetMetaData rsmd = _metaData;
      try{
         resultsList = new ArrayList<ArrayList<String>>();
         numOfFields = rsmd.getColumnCount();
         columnWidths = new int [numOfFields];

         if(rs.isBeforeFirst()){
            while(rs.next()){
               ArrayList<String> row = new ArrayList<String>();
               for(int i=1; i<=numOfFields; i++){
                  String entry = rs.getString(i);
                  if(rs.wasNull()){
                     entry = "NULL";
                  }
                  int stringWidth = entry.length();
                  if(stringWidth > columnWidths[i-1]){
                     columnWidths[i-1] = stringWidth;
                  }
                  row.add(entry);
               }
               resultsList.add(row);
            }
         }
         else{
            resultsList = null;
         }
      }
      catch(SQLException sqle){
         throw new DLException(sqle,"SQL Exception caught at connectDB.setResultingData()");
      }
      catch(Exception e){
         throw new DLException(e,"Misc Exception caught at connectDB.setResultingData()","# of Fields: " + String.valueOf(numOfFields),
                               "Column Widths: " + Arrays.toString(columnWidths),"Results List: " + Arrays.toString(resultsList.toArray()));
      }
   }

}//End connectDB
