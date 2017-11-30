/*
 * Main - launches the application
 */
package capstonetracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

/**
 *
 * @author Henry Kirk
 */
public class CapstoneTracker extends Application {

    private Stage stage;
    private FXMLLoader fxmlLoader;
    private BLUser user;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        // Show login scene at start
        changeScene("Login");

        // get login controller
        LoginController lc = (LoginController) fxmlLoader.getController();
        // add event listener to login button
        lc.btnLogin.setOnAction((event) -> {
            if(lc.login()){
                user = lc.getUser();
                changeScene("MyProjects");
            }
        });

        MyProjectsController mpc = null;
        if(user != null){
            mpc = (MyProjectsController) fxmlLoader.getController();
            // Populate table with user's projects
            // mpc.loadTable(user.getProjectIds());
            // logout
            mpc.btnLogout.setOnAction((event) -> {
                changeScene("Login");
                user = null;
            });
        }

    }

    /**
     * Create and show a scene from the fxml file name.
     */
    private void changeScene(String sceneName) {
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource(sceneName+".fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(sceneName);
            stage.centerOnScreen();
            stage.show();
        } catch(Exception e){
            System.out.println("Exception caught at changeScene"+e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }

    }

    /**
     * @param args - the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
