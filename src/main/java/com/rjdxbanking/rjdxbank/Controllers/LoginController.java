package com.rjdxbanking.rjdxbank.Controllers;

import com.rjdxbanking.rjdxbank.Clients.AccountClient;
import com.rjdxbanking.rjdxbank.Clients.BankIdentificationClient;
import com.rjdxbanking.rjdxbank.Clients.SessionClient;
import com.rjdxbanking.rjdxbank.Entity.Account;
import com.rjdxbanking.rjdxbank.Helpers.CreditCardHelper;
import com.rjdxbanking.rjdxbank.Helpers.Navigator;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane LoadingPage;

    @FXML
    private AnchorPane LoginPage;

    @FXML
    private AnchorPane PinPage;

    @FXML
    private Button btnChinese;

    @FXML
    private Button btnEnglish;

    @FXML
    private Button btnMalay;

    @FXML
    private Button card1;

    @FXML
    private Button card2;

    @FXML
    private Button card3;

    @FXML
    private ImageView iconPrimary;

    @FXML
    private PasswordField pinField;

    @FXML
    private Label invalidPinLabel;

    @FXML
    private Label loginActionLabel;

    private int attempts = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path iconPrimaryPath = FileSystems.getDefault().getPath(
                "src/main/resources/com/rjdxbanking/rjdxbank/Images/", "IconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);
    }

    @FXML
    void setLanguage(ActionEvent event) throws IOException {
        if (event.getSource() == btnEnglish) {
            Navigator.setLocale(Locale.forLanguageTag("en"), "Login");
        } else if (event.getSource() == btnChinese) {
            Navigator.setLocale(Locale.forLanguageTag("zh"), "Login");
        } else if (event.getSource() == btnMalay) {
            Navigator.setLocale(Locale.forLanguageTag("ms"), "Login");
        }
    }

    // THIS IS HARD CODED FOR NOW, BAD FORMATTING
    @FXML
    void onInsertCard(ActionEvent event) throws InterruptedException {
        LoginPage.setVisible(false);
        loginActionLabel.setText("%loadingLabel");
        LoadingPage.setVisible(true);
        if (event.getSource() == card1) {
            enterCardNumber("6229259821434671");
        } else if (event.getSource() == card2) {
            enterCardNumber("1234567890123456");
        } else if (event.getSource() == card3) {
            enterCardNumber("6219259821434673");
        }
    }

    private void enterCardNumber(String fullCardNumbe) {
        try {
            checkCardNumber(fullCardNumbe);
        } catch (Exception e) {
            LoginPage.setVisible(true);
            LoadingPage.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Card Rejected");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void checkCardNumber(String fullCardNumber) throws Exception {
        if (!(CreditCardHelper.checkLuhn(fullCardNumber))) {
            throw new Exception("Bad card reading, Please try again (Luhn Failed)");
        }
        BankIdentificationClient BinClient = new BankIdentificationClient();
        String binNum = fullCardNumber.substring(0, 6);

        if (BinClient.CheckBIN(binNum)) {
            SessionClient.setCardNum(fullCardNumber);
            SessionClient.setOwnBank(true);
            PinPage.setVisible(true);
            LoadingPage.setVisible(false);
        } else {
            throw new Exception("Does not belong to our bank");
        }
    }

    private void checkPin(String pin) throws IOException {
        AccountClient accountClient = new AccountClient();
        String cardNum = SessionClient.getCardNum().substring(6, 15);
        try {
            Account account = accountClient.Login(cardNum, pin);
            SessionClient.setAccount(account);
            Navigator.setRoot("MainDashboard");
        } catch (Exception e) {
            if (attempts < 6) {
                attempts++;
                PinPage.setVisible(true);
                LoadingPage.setVisible(false);
                invalidPinLabel.setVisible(true);
            } else {
                // Reset attempts, kicks the user back to the login page.
                attempts = 0;
                invalidPinLabel.setVisible(false);
                LoadingPage.setVisible(false);
                LoginPage.setVisible(true);
            }
        }
    }

    @FXML
    private void pinTyped(KeyEvent event) throws IOException {
        if (pinField.getText().length() >= 6) {
            PinPage.setVisible(false);
            LoadingPage.setVisible(true);

            Duration delay = Duration.millis(150);
            PauseTransition transition = new PauseTransition(delay);
            transition.setOnFinished(evt -> {
                try {
                    checkPin(pinField.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                pinField.setText("");
            });
            transition.play();
        }
    }
}
