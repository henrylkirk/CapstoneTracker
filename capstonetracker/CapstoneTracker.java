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

    private static Stage stage;
    private FXMLLoader fxmlLoader;
    public static BLUser user;
    private MyProjectsController mpc = null;
    private ProjectDetailController pdc = null;

    @Override
    public void start(Stage stage) throws Exception {
        setStage(stage);

        // Show login scene at start
        setScene("Login");
    }

    // Accessors & Mutators
    private void setStage(Stage stage) {
        CapstoneTracker.stage = stage;
    }
    static public Stage getStage() {
        return CapstoneTracker.stage;
    }
    private void setUser(BLUser user) {
        CapstoneTracker.user = user;
    }
    static public BLUser getUser() {
        return CapstoneTracker.user;
    }

    /**
     * Create and show a scene from the fxml file name.
     */
    // public void changeScene(String sceneName) {
    //     try {
    //         fxmlLoader = new FXMLLoader(getClass().getResource(sceneName+".fxml"));
    //         Parent root = fxmlLoader.load();
    //         Scene scene = new Scene(root);
    //         stage.setScene(scene);
    //         stage.setTitle(sceneName);
    //         stage.centerOnScreen();
    //         stage.show();
    //     } catch(Exception e){
    //         System.out.println("Exception caught at changeScene"+e.getMessage());
    //         e.printStackTrace();
    //         System.exit(0);
    //     }
    //
    // }

    /**
     * Create and show a scene from the fxml file name.
     */
    public static void setScene(String sceneName) {
        try {
            FXMLLoader f = new FXMLLoader(CapstoneTracker.class.getResource(sceneName+".fxml"));
            Parent root = f.load();
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
