import Clients.AuthClient;
import Helpers.DBUtil;
import Helpers.FirebaaseInitialize;
import Models.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    // public static void main(String[] args) {
    // if (!DBUtil.EstablishConnection()) {
    // System.out.println("Failed establishing a connection to MySQL Server. Please
    // try again soon.");
    // return;
    // }
    // System.out.println("Welcome to ABC Bank!");
    // System.out.println("Please select an option below");
    // System.out.println("[1] Login");
    // System.out.println("[2] Register");
    // Scanner scanner = new Scanner(System.in);
    // int optSelection;
    // do {
    // System.out.print("Please enter your selection: ");
    // optSelection = scanner.nextInt();
    // } while (optSelection != 1 && optSelection != 2);

    // // Testing out how the flow would work like
    // AuthClient client = new AuthClient();
    // String dateString = "1999-08-18";
    // Date date = Date.valueOf(dateString);

    // var test = client.Login("xavieroyj1999", "trickster123");
    // // Creating an account

    // }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        try {
            // initDatabase();
            FXMLLoader loader = new FXMLLoader(); // to load view
            loader.setLocation(getClass().getResource("GUI/PubScene/MainWindow.fxml"));
            VBox root = loader.load();

            // //controller
            // MainWindowController LOECtrl=loader.getController();
            // LOECtrl.setMainApp(this);

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