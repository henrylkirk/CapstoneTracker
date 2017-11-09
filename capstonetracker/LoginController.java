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

    // private User user = null;
    
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnCreate;
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

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // create a user
        // user = new User();
    }

    @FXML
    protected void handleLoginButtonAction(ActionEvent event) {
        System.out.println("successfully logged in");
    }

    @FXML
    protected void handleCreateButtonAction(ActionEvent event) {
        System.out.println("successfully created account");
    }

    private void login() {
        String username = tfLoginUsername.getText().trim();
        String password = tfLoginPassword.getText().trim();

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
