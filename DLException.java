import java.io.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Class to handle and log Exceptions
 * @author Ryan Sweeney
 * 09/26/17
 */
 
class DLException extends Exception{
  
  /**
   * Constructor that takes in an Exception object
   * @param _ex The Exception object passed to DLException when an Exception was caught
   */
   public DLException(Exception _ex){
      super(_ex.getMessage());
      super.setStackTrace(_ex.getStackTrace());
      this.log(null);
   }
  
  /**
   * Constructor that takes in an Exception object and multiple String variables
   * @param _ex The Exception object passed to DLException when an Exception was caught
   * @param info String variables passed into the Constructor detailing data such as the message to be used and info regarding the Exception generated
   */
   public DLException(Exception _ex, String... info){
      super(info[0]);
      super.setStackTrace(_ex.getStackTrace());
      this.log(info);  
   }
   
   /**
    * Private method the generates a Timestamp, and then writes all information gathered by the class instance to a log file
    * @param logStuff String array with specific info to be written to the log file
    */
   private void log(String [] logStuff){
   
      //Generate timestamp
      Date date = new Date();
      Timestamp timeCaught = new Timestamp(date.getTime());
      
      //Perform File Write
      try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("DatabaseErrorLog.txt",true)))){ 
         pw.write("\r\n");
         pw.write(timeCaught.toString() + "  :::  ");
         pw.write(super.getMessage());
         pw.write("\r\n");
         if(logStuff.length > 1){
            for(int i=1; i<logStuff.length; i++){
               pw.write(logStuff[i]);
               pw.write("\r\n");
            }
         }
         pw.write("Stack Trace____________\r\n");
         super.printStackTrace(pw);
         pw.write("\r\n");
         pw.flush();
      }
      catch(IOException ioe){
         ioe.printStackTrace();
      }
   }
}