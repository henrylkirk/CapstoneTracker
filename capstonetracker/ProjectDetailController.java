package capstonetracker;

import java.util.ArrayList;
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
    @FXML
    private TableView tblStatuses;
    @FXML
    private TableView tblUsers;
    private Project project;
    private String role;
    private ArrayList<ArrayList<String>> users;
    private ArrayList<Status> statuses;
    // private ObservableList<UserRow> userRows;
    // private ObservableList<StatusRow> statusRows;

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

        loadUsersTable();
        loadStatusesTable();
    }

    /**
     * Populate the "users" table with all users with a role on this project.
     */
    private void loadUsersTable(){
        // this.users = project.getUsers();
        // ArrayList<UserRow> rowList = new ArrayList<UserRow>();
		// for (int i = 0; i < users.size(); i++) {
		// 	UserRow row = new UserRow(users.get(i).getFirstName(), users.get(i).getLastName(), project.getRole(users.get(i).getUserId()));
		// 	rowList.add(row);
		// }
		// userRows = FXCollections.observableArrayList(rowList);
		// tblUsers.setItems(userRows);
        System.out.println("User table loaded");
    }

    /**
     * Populate the "users" table with all users with a role on this project.
     */
    private void loadStatusesTable(){
        // this.statuses = project.getStatuses();
        // ArrayList<StatusRow> rowList = new ArrayList<StatusRow>();
		// for (int i = 0; i < statuses.size(); i++) {
		// 	StatusRow row = new StatusRow(statuses.get(i).getStatusName(),statuses.get(i).getStatusDescription(),statuses.get(i).getLastDateModified(),statuses.get(i).getComment());
		// 	rowList.add(row);
		// }
		// statusRows = FXCollections.observableArrayList(rowList);
		// tblStatuses.setItems(statusRows);
        System.out.println("status table loaded");
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
