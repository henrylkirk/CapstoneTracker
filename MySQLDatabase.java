import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class object used to connect to a MySQL Database
 * @author Ryan Sweeney
 * Revised: 10/25/17
 */
 
public class MySQLDatabase{
 
   private String uri; //Location and DB to use
   private String driver; //Driver to use
   private String user; //Username to access DB
   private String pass; //Password to access DB
   private Connection conn; //Connection object
   private int numOfFields; //number of fields recovered when executing a query
   private ArrayList<ArrayList<String>> resultsList;//2d ArrayList to hold results from Queries
   private int [] columnWidths; //int array the stores the max widths for the columns for formatting purposes
   private boolean hasHeader; //boolean that determines if the ResultsList includes the header
   private boolean noRollback; //boolean that determines whether an transaction was rolled back
   
   /**
    * Default constructor with default options to connect to the database
    */
   public MySQLDatabase(){
      uri = "jdbc:mysql://localhost/travel?autoReconnect=true&useSSL=false";
      driver = "com.mysql.jdbc.Driver";
      user = "root";
      pass = "student";
   }
   
   /**
    * Param constructor for MySQL Database that sets specific username and password to access the database; instantiates defaults for uri and driver
    * @param _user sets value for the username
    * @param _pass sets value for the password
    */
   public MySQLDatabase(String _user, String _pass){
      user = _user;
      pass = _pass;
      uri = "jdbc:mysql://localhost/travel?autoReconnect=true&useSSL=false";
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
         throw new DLException(sqle,"SQL Exception caught at MySQLDatabase.connect()","Username: " + user,"Password: " + pass,
                                "URI: " + uri,"Driver: " + driver);
      }
      catch(Exception e){
         isConn = false;
         throw new DLException(e,"Misc Exception caught at MySQLDatabase.connect()");
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
         throw new DLException(sqle,"SQL Exception caught at MySQLDatabase.close()");
      }
      catch(Exception e){
         isClosed = false;
         throw new DLException(e,"Misc Exception caught at MySQLDatabase.close()");
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
   private PreparedStatement prepare(String _query, String [] _params) throws DLException {
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
         throw new DLException(sqle,"SQL Exception caught at MySQLDatabase.prepare()",
                                "Prepared Statement: " + query,"Parameters: " + Arrays.toString(params));
      }
      catch(Exception e){
         throw new DLException(e,"Misc Exception caught at MySQLDatabase.prepare()");
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
         throw new DLException(sqle,"SQL Exception caught at MySQLDatabase.executeStmt()","Statement: " + statement
                                ,"Parameters: " +Arrays.toString(params));
      }
      catch(Exception e){
         resultsList = null;
         throw new DLException(e,"Misc Excpetion caught at MySQLDatabase.executeStmt()");
      }
      return resultCount;
   }
   
   /**
    * Method that starts a transaction
    * @exception DLException throw when any type of exception is caught and write info to a log file
    */
   public void startTrans() throws DLException{
      try{
         conn.setAutoCommit(false);
         noRollback = true;
      }
      catch(SQLException sqle){
         throw new DLException(sqle,"SQL Exception caught at MySQLDatabase.startTrans()");
      }
      catch(Exception e){
         throw new DLException(e,"Misc Exception caught at MySQLDatabase.startTrans()");
      }
   }
   
   /**
    * Method the ends a transaction
    * @exception DLException throw when any type of exception is caught and write info to a log file
    */
   public void endTrans() throws DLException{
      try{
         if(noRollback){
            conn.commit();
         }
         conn.setAutoCommit(true);
      }
      catch(SQLException sqle){
         throw new DLException(sqle,"SQL Exception caught at MySQLDatabase.endTrans()");
      }
      catch(Exception e){
         throw new DLException(e,"Misc Exception caught at MySQLDatabase.endTrans()");
      }
   }
   
   /**
    * Method used to rollback the results of a transaction should an error occur
    * @exception DLException throw when any type of exception is caught and write info to a log file
    */
   public void rollbackTrans() throws DLException{
      try{
         conn.rollback();
         noRollback = false;
      }
      catch(SQLException sqle){
         throw new DLException(sqle,"SQL Exception caught at MySQLDatabase.rollbackTrans()");
      }
      catch(Exception e){
         throw new DLException(e,"Misc Exception caught at MySQLDatabase.rollbackTrans()");
      }
   }
   
   /**
    * Method to retrieve data from the MySQL Database; column headers not included
    * @param statement sets string to be used for the SELECT statement
    * @exception DLException catches exceptions and sends info to log file
    * @return results 2d ArrayList of values aquirred when executing the SELECT statement
    */
   public ArrayList<ArrayList<String>> getData(String statement) throws DLException {
      String query = statement;//String used for the SELECT statement
      hasHeader = false;
      try{
         Statement stmt = conn.createStatement();
         ResultSet resultSet = stmt.executeQuery(query);
         ResultSetMetaData metaData= resultSet.getMetaData();
         this.setResultingData(resultSet, metaData);
      }
      catch(SQLException sqle){
         resultsList = null;
         throw new DLException(sqle,"SQL Exception caught at MySQLDatabase.getData()","Query used: " 
                               + query);
      }
      catch(Exception e){
         resultsList = null;
         throw new DLException(e,"Misc Exception caught at MySQLDatabase.getData()");
      }
      return resultsList;
   }
   
   /**
    * Method to retrieve data from the MySQL Database with or without the header included in the ResultsList
    * @param statement sets string to be used for the SELECT statement
    * @param includeHeader boolean to determine whether or not Column headers will be included in the returned ResultsList
    * @exception DLException catches exceptions and sends info to log file
    * @return results 2d ArrayList of values aquirred when executing the SELECT statement
    */
   public ArrayList<ArrayList<String>> getData(String statement, boolean includeHeader) throws DLException {
      String query = statement;//String used for the SELECT statement
      hasHeader = includeHeader;
      try{
         Statement stmt = conn.createStatement();
         ResultSet resultSet = stmt.executeQuery(query);
         ResultSetMetaData metaData= resultSet.getMetaData();
         this.setResultingData(resultSet, metaData);
      }
      catch(SQLException sqle){
         resultsList = null;
         throw new DLException(sqle,"SQL Exception caught at MySQLDatabase.getData(statement,includeHeader)","Query used: " 
                               + query,"Header Included: " + hasHeader);
      }
      catch(Exception e){
         resultsList = null;
         throw new DLException(e,"Misc Exception caught at MySQLDatabase.getData()");
      }
      return resultsList;
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
      hasHeader = true;
      
      try{
         PreparedStatement stmt = this.prepare(query,params);
         ResultSet resultSet = stmt.executeQuery();
         ResultSetMetaData metaData= resultSet.getMetaData();
         this.setResultingData(resultSet, metaData);
      }
      catch(SQLException sqle){
         resultsList = null;
         throw new DLException(sqle,"SQL Exception caught at MySQLDatabase.getData(statement,includeHeader)","Query used: " 
                               + query,"Parameters Passed: " + Arrays.toString(params));
      }
      catch(Exception e){
         resultsList = null;
         throw new DLException(e,"Misc Exception caught at MySQLDatabase.getData()");
      }
      return resultsList;
   }
   
   /**
    * Method to perform UPDATE, INSERT and DELETE queries on the MySQL Database
    * @param statement sets string to be used for the query
    * @exception DLException catches exceptions and sends info to log file
    * @return success boolean that determines in the query ran successfully
    */
   public boolean setData(String statement) throws DLException {
      String query = statement;
      boolean success = false;
      int rc = 0;
      
      try{
         Statement stmt = conn.createStatement();
         rc = stmt.executeUpdate(query);
         //debug: System.out.println("Result count: " + rc);
         success = true;
      }
      catch(SQLException sqle){
         success = false;
         throw new DLException(sqle,"SQL Exception caught at MySQLDatabase.setData()","Query: " 
                               + query,"Result Count: " + String.valueOf(rc));
      }
      catch(Exception e){
         success = false;
         throw new DLException(e,"Misc Exception caught at MySQLDatabase.setData()");
      }
      return success;
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
         throw new DLException(e,"Misc Exception caught at MySQLDatabase.setData(statement,_params)");
      }
      return success;
   }
   
   /**
    * Method that performs any query passed to it and prints info taken from table
    * @param statement sets string to be used for the query
    * @exception DLException catches exceptions and sends info to log file
    */
   public void descTable(String statement) throws DLException{
      String query = statement;
      hasHeader = true;
      try{
         //Run query and establish Result Set and its Meta Data
         Statement stmt = conn.createStatement();
         ResultSet resultSet = stmt.executeQuery(query);
         ResultSetMetaData metaData = resultSet.getMetaData();
         this.setResultingData(resultSet,metaData);
         
         System.out.println("Number of Fields Retireved: " + numOfFields +"\n");//print numOfFields
         
         //Print Column Name w/ Types and determine length of the column generated
         for(int i=1; i<=numOfFields; i++){
            System.out.println(resultsList.get(0).get(i-1) + " ==> " + metaData.getColumnTypeName(i));
         }
         System.out.println();
         this.printQueryTable();
      }
      catch(SQLException sqle){
         throw new DLException(sqle,"SQL Exception caught at MySQLDatabase.descTable()","Query: " + query);
      }
      catch(Exception e){
         throw new DLException(e,"Misc Exception caught at MySQLDatabase.descTable()");
      }
   }
   
  /**
   * Private method to print a seperating line for output in descTable method
   * @param _width Integer to determine number of chars to be appended to the constructed line
   */
   private void printSeparatingLine(int _width){
      int numChars = _width;
      StringBuilder format = new StringBuilder();
      for(int i=1; i<= numChars; i++){
         format.append("-");
      }
      String formattedString = format.toString();
      System.out.print(formattedString + "\n");
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
      boolean willInclude = hasHeader;
      try{
         resultsList = new ArrayList<ArrayList<String>>();
         numOfFields = rsmd.getColumnCount();
         columnWidths = new int [numOfFields];
         
         if(willInclude){
            ArrayList<String> header = new ArrayList<String>();
            for(int i=1; i<=numOfFields; i++){
               String columnName = rsmd.getColumnName(i);
               int columnWidth = columnName.length();
               header.add(columnName);
               columnWidths[i-1] = columnWidth;
            }
            resultsList.add(header);
         }
         
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
         throw new DLException(sqle,"SQL Exception caught at MySQLDatabase.setResultingData()");
      }
      catch(Exception e){
         throw new DLException(e,"Misc Exception caught at MySQLDatabase.setResultingData()","# of Fields: " + String.valueOf(numOfFields), 
                               "Column Widths: " + Arrays.toString(columnWidths),"Results List: " + Arrays.toString(resultsList.toArray()));
      }
   }
   
   /**
    * Method the generates and prints a formatted table simliar to one that would be generated in the MySQL Command Line
    */
   public void printQueryTable(){
      
      boolean headerIncluded = hasHeader;
      
      StringBuilder firstLine = new StringBuilder();
      for(int i=1; i<=numOfFields; i++){
         String format = "| %-" + columnWidths[i-1] + "s ";
         firstLine.append(String.format(format,resultsList.get(0).get(i-1)));
      }
      firstLine.append("|\n");
      String formatLine = firstLine.toString();//Convert StringBuilder object to String
      int width = formatLine.length();//Get length of new String object
      
      this.printSeparatingLine(width);
      System.out.print(formatLine);
      
      if(headerIncluded){
         this.printSeparatingLine(width);
      }
           
      for(int i=1; i<resultsList.size(); i++){
         for(int j=0; j<resultsList.get(i).size(); j++){
            String format = "| %-" + columnWidths[j] + "s ";
            System.out.printf(format,resultsList.get(i).get(j));
         }
         System.out.print("|\n");
      }
      this.printSeparatingLine(width);
      System.out.println();//Print one last line to finish 
   }
   
}//End MySQLDatabase