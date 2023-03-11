import java.io.IOException;

import GUI.Navigate;
// UI imports
import javafx.application.Application;
import javafx.stage.Stage;
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