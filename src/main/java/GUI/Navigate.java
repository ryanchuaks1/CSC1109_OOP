package GUI;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

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
        if (fxml == "Login") {
            scene.setRoot(loadFXML(fxml));
        } else {
            scene.setRoot(loadFXML(fxml));

            // create transition for logout (Normally should be more than that but for now I
            // put with 10 for showcase purposes)
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
    }

    // TODO: Create transition, i tried but had problems with static variables
    protected static void logout() throws IOException {
        Navigate.setRoot("Login");
        // LoginController.LoginPage.setVisible(false);
        // LoginController.LoadingPage.setVisible(true);
        // route to logout, card eject, and sessionvalues remove
        SessionClient.setAccount(null);
        SessionClient.setCardNum(null);
        SessionClient.setOwnBank(false);
        SessionClient.setNavState(null);

        Duration delay = Duration.seconds(2);
        PauseTransition transition = new PauseTransition(delay);
        transition.setOnFinished(evt -> {
            // LoginController.loginActionLabel.setText("%returnCardLabel");
            // LoginController.LoginPage.setVisible(true);
            // LoginController.LoadingPage.setVisible(false);
        });
        transition.play();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("labelText", currentLocale);
        FXMLLoader fxmlLoader = new FXMLLoader(Navigate.class.getResource(fxml + ".fxml"), bundle);
        return fxmlLoader.load();
    }

    public static void setLocale(Locale newLocale, String fxml) throws IOException {
        currentLocale = newLocale;
        setRoot(fxml);
    }
}
