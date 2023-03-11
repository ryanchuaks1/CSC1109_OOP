package GUI;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.twilio.rest.api.v2010.account.incomingphonenumber.Local;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigate {
    private static Scene scene;
    private static Locale currentLocale;

    public static void initUserInterface(Stage stage) throws IOException {
        currentLocale = Locale.forLanguageTag("en");
        scene = new Scene(loadFXML("Login"));
        stage.setScene(scene);
        stage.setTitle("RDJX Ibanking");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("labelText", currentLocale);
        FXMLLoader fxmlLoader = new FXMLLoader(Navigate.class.getResource(fxml + ".fxml"), bundle);
        return fxmlLoader.load();
    }

    public static void setLocale(Locale newLocale, String fxml) throws IOException{
        currentLocale = newLocale;
        setRoot(fxml);
    }
}
