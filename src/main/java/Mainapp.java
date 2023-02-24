import Helpers.FirebaseInitialize;
import Entity.User;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

// UI imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Mainapp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        try {
            FirebaseInitialize.initDatabase();

            FXMLLoader loader = new FXMLLoader(); // to load view
            loader.setLocation(getClass().getResource("GUI/PubScene/MainWindow.fxml"));
            VBox root = loader.load();

            Path dPath = FileSystems.getDefault().getPath("Resources/image/", "maxresdefault.jpg");
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("Banking");
            primaryStage.getIcons().add(new Image(dPath.toUri().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}