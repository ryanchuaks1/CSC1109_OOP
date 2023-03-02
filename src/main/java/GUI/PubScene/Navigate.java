package GUI.PubScene;

import java.io.IOException;

import org.kordamp.bootstrapfx.BootstrapFX;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigate {
    private static Scene scene;

    public static void initUserInterface(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Login"));
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet()); // Add style sheet to scenes
        stage.setScene(scene);
        stage.setTitle("RDJX Ibanking");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Navigate.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
