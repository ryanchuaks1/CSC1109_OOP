import java.io.IOException;

// UI imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.kordamp.bootstrapfx.BootstrapFX;

public class Mainapp extends Application {
    public static void main(String[] args) {
        launch();
    }

    private static Scene scene;
    
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("GUI/PubScene/Login"));
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet()); // Add style sheet to scenes
        stage.setScene(scene);
        stage.setTitle("RDJX Ibanking");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Mainapp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

}