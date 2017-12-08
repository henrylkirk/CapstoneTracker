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

// TODO: remove below imports
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
/**
 * FXML Controller for my projects view
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
	private TableColumn colView;
    @FXML
    public Button btnLogout;
	private ArrayList<Button> viewBtns;
    // private ObservableList<Person> data =
    //     FXCollections.observableArrayList(
    //         new Person("Thesis 1", "Advisor"),
    //         new Person("Capstone 1", "Student"),
    //         new Person("Capstone 2", "Advisor"),
    //         new Person("Thesis 2", "Advisor")
    //     );
	private 
	private ObservableList<ProjectRow> rows;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    	// load projects in table
    	tblProjects.setEditable(true);
        colProjectName.setCellValueFactory(new PropertyValueFactory<ProjectRow,String>("projectName"));
        colRole.setCellValueFactory(new PropertyValueFactory<ProjectRow,String>("projectType"));
		colView.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ProjectRow, Boolean>,
                ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ProjectRow, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        colView.setCellFactory(
                new Callback<TableColumn<ProjectRow, Boolean>, TableCell<ProjectRow, Boolean>>() {

            @Override
            public TableCell<ProjectRow, Boolean> call(TableColumn<ProjectRow, Boolean> p) {
                return new ButtonCell();
            }

        });

        tblProjects.setItems(rows);

    }

    /**
     * Load projects associated with a user into the table.
     */
    public void loadTable(ArrayList<Project> projects){
		// data = FXCollections.observableArrayList(projects);
		ArrayList<ProjectRow> rowList = new ArrayList<ProjectRow>();
		for (int i = 0; i < projects.size(); i++) {
			System.out.println(projects.get(i).getProjectName());
			System.out.println(projects.get(i).getProjectType());
			System.out.println(projects.get(i).getProjectID());
			System.out.println(projects.get(i).getProjectDescription());
			System.out.println(projects.get(i).getStartDate());
			System.out.println(projects.get(i).getEndDate());
			System.out.println(projects.get(i).getGrade());
			ProjectRow row = new ProjectRow(projects.get(i).getProjectName(), projects.get(i).getProjectType());
			rowList.add(row);
		}
		rows = FXCollections.observableArrayList(rowList);
    }

    @FXML
    protected void handleCreateButtonAction(ActionEvent event) {
    	// data.add(new Project(1));
		// data.add(new Person("Thesis","Grad"));
    }

	// test class for adding rows to table
    public static class ProjectRow {
        // private final SimpleStringProperty firstName;
        // private final SimpleStringProperty lastName;
		private String projectName;
        private String projectType;

        private ProjectRow(String projectName, String projectType) {
            // this.firstName = new SimpleStringProperty(fName);
            // this.lastName = new SimpleStringProperty(lName);
			this.projectName = projectName;
            this.projectType = projectType;
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
    }

	// Define button cell
    private class ButtonCell extends TableCell<ProjectRow, Boolean> {
        final Button cellButton = new Button("View");

        ButtonCell(){
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    // TODO: move this functionality to CapstoneTracker.java
					System.out.println("button clicked");
					Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProjectDetail.fxml"));

					try{
						Parent root = fxmlLoader.load();
			            Scene scene = new Scene(root);
					    stage.setScene(scene);
					}catch(Exception e){
						System.out.println("button clicked"+e.getMessage());
						e.printStackTrace();
					}
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }

	// Get the "view" buttons
	public ArrayList<Button> getViewBtns(){
		ObservableList btns = colView.getColumns();
		System.out.println(btns.toString());
		return viewBtns;
	}

}
