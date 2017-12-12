package capstonetracker;

import java.util.ArrayList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

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
    private TableColumn colStatusName;
    @FXML
    private TableColumn colDescription;
    @FXML
    private TableColumn colModified;
    @FXML
    private TableColumn colComments;
    @FXML
    private TableView tblUsers;
    @FXML
    private TableColumn colRole;
    @FXML
    private TableColumn colName;
    @FXML
    private TextField tfAddUsername;
    @FXML
    private TextField tfAddRole;
    @FXML
    private Button btnAddUser;
    @FXML
    private TextField tfAddComment;
    @FXML
    private Button tfAddStatus;
    private Project project;
    private String role;
    private BLUser user;
    private ArrayList<User> users;
    private ArrayList<Status> statuses;
    private ObservableList<UserRow> userRows;
    private ObservableList<StatusRow> statusRows;

    /**
    * Initializes the controller class.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set each table column type
		tblUsers.setEditable(true);
		colName.setCellValueFactory(new PropertyValueFactory<UserRow,String>("name"));
		colRole.setCellValueFactory(new PropertyValueFactory<UserRow,String>("role"));

        // set each table column type
		tblStatuses.setEditable(true);
		colStatusName.setCellValueFactory(new PropertyValueFactory<StatusRow,String>("statusName"));
		colDescription.setCellValueFactory(new PropertyValueFactory<StatusRow,String>("description"));
        colModified.setCellValueFactory(new PropertyValueFactory<StatusRow,String>("lastDateModified"));
        colComments.setCellValueFactory(new PropertyValueFactory<StatusRow,String>("comment"));
    }

    public void loadProjectDetails(Project project, BLUser user){
        this.project = project;
        this.user = user;
        role = project.getRole(user.getUserId());

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
        this.users = project.getUsers();
        ArrayList<UserRow> rowList = new ArrayList<UserRow>();
		for (int i = 0; i < users.size(); i++) {
            int uid = users.get(i).getUserId();
			UserRow row = new UserRow(users.get(i).getFirstName(), users.get(i).getLastName(), project.getRole(uid), uid);
            System.out.println("first name: "+users.get(i).getFirstName());
            System.out.println("user added to table");
            rowList.add(row);
		}
		userRows = FXCollections.observableArrayList(rowList);
		tblUsers.setItems(userRows);
        System.out.println("User table loaded");
    }

    /**
     * Populate the "users" table with all users with a role on this project.
     */
    private void loadStatusesTable(){
        this.statuses = project.getStatus();
        ArrayList<StatusRow> rowList = new ArrayList<StatusRow>();
		for (int i = 0; i < statuses.size(); i++) {
			StatusRow row = new StatusRow(statuses.get(i).getStatusName(),statuses.get(i).getStatusDescription(),statuses.get(i).getLastDateModified(),statuses.get(i).getComment());
			rowList.add(row);
		}
		statusRows = FXCollections.observableArrayList(rowList);
		tblStatuses.setItems(statusRows);
        System.out.println("status table loaded");
    }

    /**
     * Add a user to this project with the input role.
     */
    @FXML
    protected void handleAddButtonAction(ActionEvent event) {
        // get values from textfields
        String addRole = tfAddRole.getText().trim();
        String addUsername = tfAddUsername.getText().trim();

        // add user with that role to this project
        project.addUser(addUsername, project.getProjectID(), addRole);
        loadUsersTable();
    }

    /**
     * Increment the status and add a comment for that status.
     */
    @FXML
    protected void handleAddStatusButton(ActionEvent event) {
        String comment = tfAddComment.getText().trim();
        project.updateStatus(comment);
        loadStatusesTable();
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

        if(project.checkProject()){
            // Update database with project fields
            project.updateProjectInfo(role);
        } else {
            project.addNewProject(user.getUserId());
        }

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

    // Class to hold user info as a table row
	public static class UserRow {
		private String name;
		private String role;
        private int uid;

		private UserRow(String fName, String lName, String role, int uid) {
			name = fName + " " + lName;
			this.role = role;
			this.uid = uid;
		}

		public String getName() {
			return name;
		}
		public String getRole() {
			return role;
		}
		public int getUserID() {
			return uid;
		}
	}

    // Class to hold user info as a table row
	public static class StatusRow {
		private String statusName;
		private String description;
        private String lastDateModified;
        private String comment;

		private StatusRow(String statusName, String description, String lastDateModified, String comment) {
			this.statusName = statusName;
            this.description = description;
			this.lastDateModified = lastDateModified;
			this.comment = comment;
		}

		public String getStatusName() {
			return statusName;
		}
		public String getDescription() {
			return description;
		}
        public String getLastDateModified() {
			return lastDateModified;
		}
		public String getComment() {
			return comment;
		}
	}
}
