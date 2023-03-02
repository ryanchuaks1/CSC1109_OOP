import java.io.IOException;

// UI imports
import javafx.application.Application;
import javafx.stage.Stage;

import GUI.PubScene.Navigate;
import Helpers.FirebaseInitialize;

public class Mainapp extends Application {
    public static void main(String[] args) {
        FirebaseInitialize.initDatabase();
        launch();
    }
    
    public void start(Stage stage) throws IOException {
        Navigate.initUserInterface(stage);
    }
}