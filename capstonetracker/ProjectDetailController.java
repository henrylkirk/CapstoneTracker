package capstonetracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.stage.Stage;
// import javafx.scene.Node;

/**
* FXML Controller for project detail view
* @author Henry Kirk
*/
public class ProjectDetailController implements Initializable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfType;
    @FXML
    private TextArea taDescription;
    @FXML
    private TextField tfStartTerm;
    @FXML
    private TextField tfEndDate;
    @FXML
    private TextField tfGrade;
    @FXML
    private Label lblPlagiarismScore;
    @FXML
    private TextField tfPlagiarismScore;
    private Project project;
    private String role;

    /**
    * Initializes the controller class.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void loadProjectDetails(Project project, String role){
        this.project = project;
        this.role = role;

        // hide/disable fields if the user is a student
        if(role.equalsIgnoreCase("Grad")) {
            tfPlagiarismScore.setVisible(false);
            lblPlagiarismScore.setVisible(false);
            tfGrade.setEditable(false);
        }

        // set textfield text
        tfName.setText(project.getProjectName());
        taDescription.setText(project.getProjectDescription());
        tfType.setText(project.getProjectType());
        tfStartTerm.setText(String.valueOf(project.getStartDate()));
        tfEndDate.setText(project.getEndDate());
        tfGrade.setText(String.valueOf(project.getGrade()));
        tfPlagiarismScore.setText(String.valueOf(project.getPlagiarismScore()));
    }

    /**
    * Get all values from inputs, set them in the project, and update that project in the database.
    */
    @FXML
    protected void handleSaveButtonAction(ActionEvent event) {
        // Get field values
        String pName = tfName.getText().trim();
        String pDesc = taDescription.getText().trim();
        String pType = tfType.getText().trim();
        String pStartTerm = tfStartTerm.getText().trim();
        String pEndDate = tfEndDate.getText().trim();
        String pGrade = tfGrade.getText().trim();
        String pPlag = tfPlagiarismScore.getText().trim();

        // Set project fields
        project.setProjectName(pName);
        project.setProjectType(pType);
        project.setProjectDescription(pDesc);
        project.setStartDate(pStartTerm);
        project.setEndDate(pEndDate);
        project.setGrade(Integer.parseInt(pGrade));
        project.setPlagiarismScore(Integer.parseInt(pPlag));

        // Update database with project fields
        project.updateProjectInfo(role);
    }

    /**
    * Get all values from inputs, set them in the project, and update that project in the database.
    */
    @FXML
    protected void handleBackButtonAction(ActionEvent event) {
        CapstoneTracker.setScene("MyProjects");
    }

    /**
    * Go back to login scene.
    */
    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) {
        CapstoneTracker.setScene("Login");
    }
}
