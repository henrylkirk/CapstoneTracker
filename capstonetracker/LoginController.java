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
    @FXML
    public Button btnLogin;
    @FXML
    private Label lblLoginStatus;
    private BLUser user = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Attempt to log in with the provided username and password.
     * This method is called when the "Login" button is pressed.
     */
    public boolean login() {
        String username = tfLoginUsername.getText();
        String password = tfLoginPassword.getText();
        boolean loginStatus = false;

        if( !isEmpty(username) && !isEmpty(password) ) {
            username = username.trim();
            password = password.trim();
            user = new BLUser(username, password);
            loginStatus = user.login();
        } else {
            loginStatus = false;
        }
        if(!loginStatus){
            System.out.println("invalid username or password");
            // show status label
            lblLoginStatus.setVisible(true);
        } else {
            // hide status label
            lblLoginStatus.setVisible(false);
            System.out.println("successful login\nUsername: "+username+"\nPassword: "+password);
        }
        return true;
    }

    /**
     * Create an account with the input information.
     * This method is called when the "Create Account" button is pressed.
     */
    @FXML
    protected void handleCreateButtonAction(ActionEvent event) {
        String username = tfCreateUsername.getText();
        String password = tfCreateUsername.getText();
        String email = tfEmail.getText();
        String firstName = tfFirstname.getText();
        String lastName = tfLastname.getText();

        System.out.println("successfully created account");
        // TODO
    }

    public BLUser getUser(){
        return user;
    }

    private static boolean isEmpty(String s) {
        if ((s != null) && (s.trim().length() > 0)){
            return false;
        } else {
            return true;
        }
    }

    private void createAccount() {
        // TODO
        // check if they exist (try to log in)
    }

}
