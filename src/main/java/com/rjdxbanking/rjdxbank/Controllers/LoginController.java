package com.rjdxbanking.rjdxbank.Controllers;

import com.rjdxbanking.rjdxbank.Clients.BankIdentificationClient;
import com.rjdxbanking.rjdxbank.Clients.PhoneClient;
import com.rjdxbanking.rjdxbank.Clients.PinClient;
import com.rjdxbanking.rjdxbank.Clients.SessionClient;
import com.rjdxbanking.rjdxbank.Helpers.CreditCardHelper;
import com.rjdxbanking.rjdxbank.Helpers.Navigator;
import com.rjdxbanking.rjdxbank.Services.AccountService;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private ImageView iconPrimary;

    @FXML
    private PasswordField pinField;

    @FXML
    private Label invalidPinLabel;

    @FXML
    private Label loadingLabel;

    @FXML
    private Label returnCardLabel;

    @FXML
    private Label readingCardLabel;

    @FXML
    private Label InvalidCardLabel;

    private int attempts = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path iconPrimaryPath = FileSystems.getDefault().getPath(
                "src/main/resources/com/rjdxbanking/rjdxbank/Images/", "IconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);
        PinPage.setVisible(false);
        LoadingPage.setVisible(false);
        if (SessionClient.getNavState() == "Logout") {
            dispenseCard();
            SessionClient.setNavState("none");
        }
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

    @FXML
    void onInsertCard(ActionEvent event) {
        String cardNum = ((Button) event.getSource()).getText().replaceAll("\\s+", "");
        // Play reading card screen (simulate delay for machine to read card)
        LoadingPage.setVisible(true);
        readingCardLabel.setVisible(true);
        Duration delay = Duration.seconds(1);
        PauseTransition transition = new PauseTransition(delay);
        transition.setOnFinished(evt -> {
            LoadingPage.setVisible(false);
            readingCardLabel.setVisible(false);
            checkCardNumber(cardNum);
        });
        transition.play();
    }

    // When user insert the card, we will check if the card is valid or in our
    // database table (Supported by our bank or not)
    private void checkCardNumber(String fullCardNumber) {
        BankIdentificationClient BinClient = new BankIdentificationClient();
        AccountService accountService = new AccountService();

        SessionClient.setCardNum(fullCardNumber);

        String binNum = fullCardNumber.substring(0, 6);
        String accountNum = fullCardNumber.substring(6, 15);

        if (CreditCardHelper.checkLuhn(fullCardNumber) // Check if is a valid card using luhn algo
                && BinClient.isValidBank(binNum)) { // Check if the bank exist in our database
            if (SessionClient.isOwnBank()) { // Check if is our own bank
                if (accountService.getAccountsByNumber(accountNum).getIsLocked()) { // check if account is locked
                    InvalidCardLabel.setVisible(true);
                    dispenseCard();
                } else {
                    PinPage.setVisible(true);
                    pinField.requestFocus();
                }
            } else { // Dont need to check for account lock if it's not our own bank card
                PinPage.setVisible(true);
                pinField.requestFocus();
            }
        } else { // Dispense card if card luhn failed or not valid
            InvalidCardLabel.setVisible(true);
            dispenseCard();
        }
    }

    private void checkPin(String pin) throws IOException {
        if (PinClient.checkPin(pin)) {
            if (SessionClient.isOwnBank()) {
                Navigator.setRoot("MainDashboard");
            } else {
                Navigator.setRoot("OtherBankWithdrawal");
            }
        } else {
            loadingLabel.setVisible(false);
            LoadingPage.setVisible(false);
            if (attempts < 5) {
                attempts++;
                PinPage.setVisible(true);
                pinField.requestFocus();
                invalidPinLabel.setVisible(true);
            } else {
                // set cardNum probably can simplify
                PhoneClient phoneOTP = new PhoneClient();
                String accountNum = SessionClient.getCardNum().substring(6, 15);
                AccountService accountService = new AccountService();
                phoneOTP.warning(accountService.getAccountsByNumber(accountNum));
                // temp set to true to locked
                accountService.updateAccountStatus(accountService.getAccountsByNumber(accountNum), true);

                invalidPinLabel.setVisible(false);
                SessionClient.setCardNum(null);
                SessionClient.setOwnBank(false);
                attempts = 0;
                dispenseCard();
            }
        }
    }

    @FXML
    private void pinTyped(KeyEvent event) throws IOException {
        if (pinField.getText().length() >= 6) {
            PinPage.setVisible(false);
            loadingLabel.setVisible(true);
            LoadingPage.setVisible(true);
            Duration delay = Duration.millis(200);
            PauseTransition transition = new PauseTransition(delay);
            transition.setOnFinished(evt -> {
                try {
                    checkPin(pinField.getText());
                    pinField.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            transition.play();
        }
    }

    @FXML
    public void dispenseCard() {
        LoadingPage.setVisible(true);
        returnCardLabel.setVisible(true);
        Duration delay = Duration.seconds(2);
        PauseTransition transition = new PauseTransition(delay);
        transition.setOnFinished(evt -> {
            LoadingPage.setVisible(false);
            returnCardLabel.setVisible(false);
            InvalidCardLabel.setVisible(false);
        });
        transition.play();
    }
}
