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
import javafx.scene.input.MouseEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
* FXML Controller for my projects view.
* @author Henry Kirk
*/
public class MyProjectsController implements Initializable {

	@FXML
	private TableView tblProjects;
	@FXML
	private TableColumn colProjectName;
	@FXML
	private TableColumn colRole;
	@FXML
	private TableColumn colId;
	@FXML
	public Button btnLogout;
	private ArrayList<Project> projects;
	private BLUser user;
	private ObservableList<ProjectRow> rows;

	/**
	* Initializes the controller class.
	*/
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		// set each table column type
		tblProjects.setEditable(true);
		colProjectName.setCellValueFactory(new PropertyValueFactory<ProjectRow,String>("projectName"));
		colRole.setCellValueFactory(new PropertyValueFactory<ProjectRow,String>("projectType"));
		colId.setCellValueFactory(new PropertyValueFactory<ProjectRow,Integer>("projectID"));

		// When a table row is double clicked on, view that project's details.
		tblProjects.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() > 1) {
				if (tblProjects.getSelectionModel().getSelectedItem() != null) {
					ProjectRow selectedRow = (ProjectRow)tblProjects.getSelectionModel().getSelectedItem();
					// find project with that id
					for(Project project : projects) {
						if(project.getProjectID() == selectedRow.getProjectID()) {
							showProjectDetails(project);
						}
					}
				}
			}
		});

		loadTable();
	}

	/**
	 * Change the scene to the project detail scene.
	 */
	private void showProjectDetails(Project project){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProjectDetail.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = CapstoneTracker.getStage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			// get controller
			ProjectDetailController pdc = (ProjectDetailController) fxmlLoader.getController();
			pdc.loadProjectDetails(project, user);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	* Load projects associated with a user into the table.
	*/
	public void loadTable(){
		this.user = CapstoneTracker.getUser();
		this.projects = user.getProjects();
		ArrayList<ProjectRow> rowList = new ArrayList<ProjectRow>();
		for (int i = 0; i < projects.size(); i++) {
			ProjectRow row = new ProjectRow(projects.get(i).getProjectName(), projects.get(i).getRole(user.getUserId()), projects.get(i).getProjectID());
			rowList.add(row);
		}
		rows = FXCollections.observableArrayList(rowList);
		tblProjects.setItems(rows);
	}

	/**
	 * Create a new empty project row.
	 */
	@FXML
	protected void handleCreateButtonAction(ActionEvent event) {
		ProjectRow row = new ProjectRow("","",-1);
		rows.add(row);
		Project project = new Project();
		// project.setRole("GRAD");
		showProjectDetails(project);
		project.addNewProject(user.getUserId());
	}

	/**
	 * Called when logout button is clicked.
	 */
	@FXML
	protected void handleLogoutButtonAction(ActionEvent event) {
		CapstoneTracker.setScene("Login");
	}

	// Class to hold project info as a table row
	public static class ProjectRow {
		private String projectName;
		private String projectType;
		private int projectID;

		private ProjectRow() {
			projectName = "";
			projectType = "";
			projectID = -1;
		}

		private ProjectRow(String projectName, String projectType, int projectID) {
			this.projectName = projectName;
			this.projectType = projectType;
			this.projectID = projectID;
		}

		public String getProjectName() {
			return projectName;
		}
		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		public String getProjectType() {
			return projectType;
		}
		public void setProjectType(String projectType) {
			this.projectType = projectType;
		}
		public int getProjectID() {
			return projectID;
		}
	}
}
