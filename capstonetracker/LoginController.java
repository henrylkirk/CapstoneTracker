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
    private PasswordField pfPassword;
    @FXML
    private TextField tfCreateUsername;
    @FXML
    private PasswordField pfCreatePassword;
    @FXML
    private TextField tfFirstname;
    @FXML
    private TextField tfLastname;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfOfficeNumber;
    @FXML
    private TextField tfPhone;
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
     @FXML
     protected void handleLoginButtonAction(ActionEvent event) {
        String username = tfLoginUsername.getText();
        String password = pfPassword.getText();
        boolean validLogin = false;

        if( !isEmpty(username) && !isEmpty(password) ) {
            username = username.trim();
            password = password.trim();
            user = new BLUser(username, password);
            validLogin = user.login();
        } else {
            validLogin = false;
        }
        if(!validLogin){
            System.out.println("invalid username or password");
            // show status label
            lblLoginStatus.setVisible(true);
        } else {
            // hide status label
            lblLoginStatus.setVisible(false);
            System.out.println("successful login\nUsername: "+username+"\nPassword: "+password);
        }
        if(validLogin){
            CapstoneTracker.user = getUser();
            CapstoneTracker.setScene("MyProjects");
        }
    }

    /**
     * Create an account with the input information.
     * This method is called when the "Create Account" button is pressed.
     */
    @FXML
    protected void handleCreateButtonAction(ActionEvent event) {
        String username = tfCreateUsername.getText();
        String password = pfCreatePassword.getText();
        String email = tfEmail.getText();
        String firstName = tfFirstname.getText();
        String lastName = tfLastname.getText();
        String office = tfOfficeNumber.getText();
        String phone = tfPhone.getText();
        String usertype = cbRole.getValue().toString();

        if(!isEmpty(username) && !isEmpty(password) && !isEmpty(email) && !isEmpty(firstName) && !isEmpty(lastName) && !isEmpty(phone) && !isEmpty(office) && !isEmpty(usertype)){
            System.out.println("successfully created account");
            user = new BLUser(username, password, firstName, lastName, email, office, phone, usertype);
            user.registerAccount();
            // System.out.println(username+password+firstName+lastName+email+phone+office+usertype);
        } else {
            System.out.println("Could not create account");
        }
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
