package capstonetracker;
/**
 * Business layer User class
 * @author Ryan Sweeney
 * Revised: 11/06/17
 */
public class BLUser extends User {


   /**
    * Parameterized Constructor for Busniess Layer User
    * @param _user Sets username for the user object
    * @param _pass Sets password for user object
    */
   public BLUser(String _user, String _pass){
      super(_user,_pass);
   }
   
   public BLUser(String _user, String _pass, String _fname, String _lname, String _email, String _office, String _phone, String _userType){
      super(_user,_pass,_fname,_lname,_email,_office,_phone,_userType);
   }
   
}