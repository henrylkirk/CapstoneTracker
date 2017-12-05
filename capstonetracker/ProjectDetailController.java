package capstonetracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * FXML Controller for project detail view
 * @author Henry Kirk
 */
public class ProjectDetailController implements Initializable {

    @FXML
    private TextField tfName;
    @FXML
    private TextArea tfDescription;
    @FXML
    private TextField tfCode;
    @FXML
    private TextField tfEndDate;
    @FXML
    private TextField tfDefenseDate;
    @FXML
    private TextField tfGrade;
    @FXML
    private TextField tfPlagiarismScore;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void loadProjectDetails(){
        tfName.setText("test");
        tfDescription.setText("test");
        tfCode.setText("test");
        tfEndDate.setText("test");
        tfDefenseDate.setText("test");
        tfGrade.setText("test");
        tfPlagiarismScore.setText("test");
    }

    @FXML
    protected void handleSaveButtonAction(ActionEvent event) {
    }

}
