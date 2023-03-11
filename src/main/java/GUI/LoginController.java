package GUI;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;

import Clients.AccountClient;
import Clients.BankIdentificationClient;
import Helpers.CreditCardHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginController implements Initializable {

    @FXML
    private Button btnChinese;

    @FXML
    private Button btnEnglish;

    @FXML
    private Button btnMalay;

    @FXML
    private ImageView iconPrimary;

    @FXML
    private Button card1;

    @FXML
    private Button card2;

    @FXML
    private Button card3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path iconPrimaryPath = FileSystems.getDefault().getPath("src/main/resources/images/", "IconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);
    }

    @FXML
    void setLanguage(ActionEvent event) throws IOException {
        if (event.getSource() == btnEnglish) {
            Navigate.setLocale(Locale.forLanguageTag("en"), "Login");
        } else if (event.getSource() == btnChinese) {
            Navigate.setLocale(Locale.forLanguageTag("zh"), "Login");
        } else if (event.getSource() == btnMalay) {
            Navigate.setLocale(Locale.forLanguageTag("ms"), "Login");
        }
    }

    // THIS IS HARD CODED FOR NOW, BAD FORMATTING
    @FXML
    void onInsertCard(ActionEvent event) {
        if (event.getSource() == card1) {
            try {
                tryLogin("6229259821434671");
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Card Rejected");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else if (event.getSource() == card2) {
            try {
                tryLogin("1234567890123456");
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Card Rejected");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else if (event.getSource() == card3) {
            try {
                tryLogin("6219259821434673");
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Card Rejected");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private static void tryLogin(String fullCardNumber) throws Exception {
        if ((CreditCardHelper.checkLuhn(fullCardNumber)) == false) {
            throw new Exception("Luhn returned false");
        }

        AccountClient accountClient = new AccountClient();
        BankIdentificationClient BinClient = new BankIdentificationClient();
        String binNum = fullCardNumber.substring(0, 6);
        String cardNum = fullCardNumber.substring(6, 15);

        if (BinClient.CheckBIN(binNum)) {
            throw new Exception("Belongs to our bank");
        } else {
            throw new Exception("Does not belong to our bank");
        }
    }
}
