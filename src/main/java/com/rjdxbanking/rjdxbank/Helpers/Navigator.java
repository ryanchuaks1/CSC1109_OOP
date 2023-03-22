package com.rjdxbanking.rjdxbank.Helpers;

import com.rjdxbanking.rjdxbank.Clients.SessionClient;
import com.rjdxbanking.rjdxbank.Clients.TimeoutClient;
import com.rjdxbanking.rjdxbank.MainApplication;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Navigator {
    private static Scene scene;
    private static Locale currentLocale;
    private static TimeoutClient timeout = new TimeoutClient();
    static EventHandler<InputEvent> inputHandler = new EventHandler<InputEvent>() {
        public void handle(InputEvent event) {
            timeout.resetTimer();
        }
    };

    public static void initUserInterface(Stage stage) throws IOException {
        currentLocale = Locale.forLanguageTag("en");
        scene = new Scene(loadFXML("Login"));
        stage.setScene(scene);
        stage.setTitle("RDJX Ibanking");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        if (fxml.equals("Login")) {
            timeout.stopTimer();
            scene.removeEventFilter(InputEvent.ANY, inputHandler);
            scene.setRoot(loadFXML(fxml));
        } else {
            timeout.startTimer();
            scene.addEventFilter(InputEvent.ANY, inputHandler);
            scene.setRoot(loadFXML(fxml));
        }
    }

    public static void logout() throws IOException {
        // route to logout, card eject, and sessionvalues remove
        SessionClient.setAccount(null);
        SessionClient.setCardNum(null);
        SessionClient.setOwnBank(false);
        SessionClient.setNavState("Logout");
        Navigator.setRoot("Login");
    }

    private static Parent loadFXML(String fxml) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("labelText", currentLocale);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(String.format("Views/%s.fxml", fxml)),
                bundle);
        return fxmlLoader.load();
    }

    public static void setLocale(Locale newLocale, String fxml) throws IOException {
        currentLocale = newLocale;
        setRoot(fxml);
    }
}
