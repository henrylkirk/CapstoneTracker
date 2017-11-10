package capstonetracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler; 
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * FXML Controller for Login
 * @author Henry Kirk
 */
public class LoginController implements Initializable {
    
    @FXML 
    private TextField tfLoginUsername;
    @FXML 
    private TextField tfLoginPassword;
    @FXML 
    private TextField tfCreateUsername;
    @FXML 
    private TextField tfCreatePassword;
    @FXML 
    private TextField tfFirstname;
    @FXML 
    private TextField tfLastname;
    @FXML 
    private TextField tfEmail;
    @FXML 
    private ComboBox cbRole;
    @FXML 
    private Label lblLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    protected void handleLoginButtonAction(ActionEvent event) {
        String username = tfLoginUsername.getText();
        String password = tfLoginPassword.getText();
        boolean loginStatus = false;

        if( !isEmpty(username) && !isEmpty(password) ) {
            username = username.trim();
            password = password.trim();

            // create a user
            User user = new User(username, password);

            loginStatus = user.login();
        } else {
            loginStatus = false;
        }

        if(!loginStatus){
            System.out.println("invalid username or password");
        } else {
            System.out.println("successful login\nUsername: "+username+"\nPassword: "+password);
        }

    }

    @FXML
    protected void handleCreateButtonAction(ActionEvent event) {
        System.out.println("successfully created account");
    }

    private static boolean isEmpty(String s) {
        if ((s != null) && (s.trim().length() > 0)){
            return false;
        } else {
            return true;
        }
    }

    private void login() {

        // String username = tfLoginUsername.getText();
        // String password = tfLoginPassword.getText();

        // if(username.length() > 0 && password.length() > 0){
        //     if(user.login(username, password)){
        //         System.out.println("Successful login");
        //     } else {
        //         System.out.println("Invalid login");
        //     }
        // } else {
        //     System.out.println("Please fill in all fields");
        // }
    }

    private void createAccount() {
        // TODO
        // check if they exist (try to log in)
    }  
    
}
