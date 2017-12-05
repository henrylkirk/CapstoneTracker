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
	private Button[] viewBtns;
	private ArrayList<Project> projects;
    private ObservableList<Person> data =
        FXCollections.observableArrayList(
            new Person("Thesis 1", "Advisor"),
            new Person("Capstone 1", "Student"),
            new Person("Capstone 2", "Advisor"),
            new Person("Thesis 2", "Advisor")
        );

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    	// load projects in table
    	tblProjects.setEditable(true);
        colProjectName.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));
        colRole.setCellValueFactory(new PropertyValueFactory<Person,String>("lastName"));
		colView.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Person, Boolean>,
                ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Person, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        colView.setCellFactory(
                new Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>>() {

            @Override
            public TableCell<Person, Boolean> call(TableColumn<Person, Boolean> p) {
                return new ButtonCell();
            }

        });

        tblProjects.setItems(data);

    }

    /**
     * Load projects associated with a user into the table.
     */
    public void loadTable(ArrayList<Project> projects){
		// data = FXCollections.observableArrayList(projects);
		// System.out.println(projects.toString());
    }

    @FXML
    protected void handleCreateButtonAction(ActionEvent event) {
    	// data.add(new Project(1));
		data.add(new Person("Thesis","Grad"));
    }

	// test class for adding rows to table
    public static class Person {
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;

        private Person(String fName, String lName) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
        }

        public String getFirstName() {
            return firstName.get();
        }
        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public String getLastName() {
            return lastName.get();
        }
        public void setLastName(String fName) {
            lastName.set(fName);
        }
    }

	// Define button cell
    private class ButtonCell extends TableCell<Person, Boolean> {
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

}
