package capstonetracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * LoginController
 * @author Henry Kirk
 */
public class LoginController implements Initializable {
    
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnCreate;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        // TODO
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set user input listeners
        btnLogin.setOnAction(event -> {getLoginInput();});
        btnCreate.setOnAction(event -> {getCreateInput();});
        // TODO
    }  

    private void getLoginInput() {
        // TODO
    }
    
    private void getCreateInput() {
        // TODO
    }  
    
}
