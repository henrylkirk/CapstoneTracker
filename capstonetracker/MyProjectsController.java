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
	private ArrayList<Project> projects;
	private ObservableList<ProjectRow> rows;
	private int projectRowIndex = 0;

	public static int getProjectRowIndex(){
		return 0;
	}

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
				projectRowIndex++;
				return new ButtonCell();
            }
        });
    }

    /**
     * Load projects associated with a user into the table.
     */
    public void loadTable(ArrayList<Project> projects){
		this.projects = projects;
		ArrayList<ProjectRow> rowList = new ArrayList<ProjectRow>();
		for (int i = 0; i < projects.size(); i++) {
			ProjectRow row = new ProjectRow(projects.get(i).getProjectName(), projects.get(i).getProjectType());
			rowList.add(row);
		}
		rows = FXCollections.observableArrayList(rowList);
		tblProjects.setItems(rows);
    }

    @FXML
    protected void handleCreateButtonAction(ActionEvent event) {
		ProjectRow row = new ProjectRow("","");
		rows.add(row);
    }

	// Class to hold project info as a table row
    public static class ProjectRow {
		private String projectName;
        private String projectType;

		private ProjectRow() {
			projectName = "";
			projectType = "";
		}

        private ProjectRow(String projectName, String projectType) {
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
			cellButton.setId(Integer.toString(projects.get(MyProjectsController.getProjectRowIndex()).getProjectID()));
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    // TODO: move this functionality to CapstoneTracker.java
					System.out.println("button clicked with ID: "+cellButton.getId());
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
