package capstonetracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler; 
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public Button btnLogout;
    private final ObservableList<Person> data =
        FXCollections.observableArrayList(
            new Person("A", "Z", "a@example.com"),
            new Person("B", "X", "b@example.com"),
            new Person("C", "W", "c@example.com"),
            new Person("D", "Y", "d@example.com"),
            new Person("E", "V", "e@example.com")
        );  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	// create a test project
    	// Project proj = new Project(1);

    	// load project in table
    	tblProjects.setEditable(true);
        colProjectName.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));
        colRole.setCellValueFactory(new PropertyValueFactory<Person,String>("lastName"));                    
        tblProjects.setItems(data);

    }

    /**
     * Load projects associated with a user into the table.
     */
    public void loadTable(User user){
    	user.getProjectIds();
    }

    @FXML
    protected void handleCreateButtonAction(ActionEvent event) {
    	data.add(new Person("new","new","new"));
    }

    @FXML
    protected void handleSaveButtonAction(ActionEvent event) {
    }

    public static class Person {
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;

        private Person(String fName, String lName, String email) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
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
       
        public String getEmail() {
            return email.get();
        }
        public void setEmail(String fName) {
            email.set(fName);
        }
       
    }
    
}