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
    private TextArea taDescription;
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
    private Project userProject;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void loadProjectDetails(Project p){
        System.out.println("project details loaded");
        userProject = p;
        tfName.setText(p.getProjectName());
        taDescription.setText(p.getProjectDescription());
        // tfCode.setText(String.valueOf(p.getProjectCode()));
        // tfStartDate.setText(p.getStartDate());
        tfEndDate.setText(p.getEndDate());
        // tfDefenseDate.setText(p.getDefenseDate());
        tfGrade.setText(String.valueOf(p.getGrade()));
        tfPlagiarismScore.setText(String.valueOf(p.getPlagiarismScore()));
    }

    @FXML
    protected void handleSaveButtonAction(ActionEvent event) {
      // String pName = tfName.getText();
      // String pDesc = tfDescription.getText();
      // String pCode = tfCode.getText();
      // String pStartDate = tfStartDate.getText();
      // String pEndDate = tfEndDate.getText();
      // String pDefense = tfDefenseDate.getText();
      // String pGrade = tfGrade.getText();
      // String pPlag = tfPlagiarismScore.getTest();
      //
      // userProject.setProjectName(pName);
      // userProject.setProjectDescription(pDesc);
      // userProject.setProjectCode(Integer.parseInt(pCode));
      // userProject.setProjectStartDate(pStartDate);
      // userProject.setProjectEndDate(pEndDate);
      // userProject.setProjectDefenseDate(pDefense);
      // userProject.setProjectGrade(Integer.parseInt(pGrade));
      // userProject.setProjectPlagiarismScore(Integer.parseInt(pPlag));
      //
      // userProject.updateProjectInfo();
    }

}
