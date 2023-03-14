package GUI;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.twilio.rest.api.v2010.account.incomingphonenumber.Local;

import Clients.SessionClient;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

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

        // create transition for logout (Normally should be more than that but for now I put with 10 for showcase purposes)
        Duration delay = Duration.seconds(30);
        PauseTransition transition = new PauseTransition(delay);
        transition.setOnFinished(evt -> {
            try {
                logout();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        // restart transition on user interaction
        scene.addEventFilter(InputEvent.ANY, evt -> transition.playFromStart());
        transition.play();
    }

    private static void logout() throws IOException{
        //route to logout, card eject, and sessionvalues remove
        Navigate.setRoot("Login");
        SessionClient.setAccount(null);
        SessionClient.setCardNum(null);
        SessionClient.setOwnBank(false);
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
