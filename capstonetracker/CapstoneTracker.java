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
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        changeScene("Login.fxml");
        
        // get login controller
        LoginController lc = (LoginController) fxmlLoader.getController();
        // get login button
        lc.btnLogin.setOnAction((event) -> {
            if(lc.login()){
                try {
                    changeScene("MyProjects.fxml");
                } catch(Exception e){}
            }
        });
    }

    private void changeScene(String sceneName) throws Exception {
        fxmlLoader = new FXMLLoader(getClass().getResource(sceneName));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
