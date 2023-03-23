package com.rjdxbanking.rjdxbank.Controllers;

import com.rjdxbanking.rjdxbank.Clients.AccountClient;
import com.rjdxbanking.rjdxbank.Clients.BankIdentificationClient;
import com.rjdxbanking.rjdxbank.Clients.PhoneOTPClient;
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
            SessionClient.setNavState(null);
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
        Duration delay = Duration.seconds(2);
        PauseTransition transition = new PauseTransition(delay);
        transition.setOnFinished(evt -> {
            LoadingPage.setVisible(false);
            readingCardLabel.setVisible(false);
            checkCardNumber(cardNum);
        });
        transition.play();
    }

    private void checkCardNumber(String fullCardNumber) {
        if (!(CreditCardHelper.checkLuhn(fullCardNumber))) {
            dispenseCard();
        } else {
            BankIdentificationClient BinClient = new BankIdentificationClient();
            String binNum = fullCardNumber.substring(0, 6);
            String accountNum = fullCardNumber.substring(6, 15);
            AccountService accountService = new AccountService();
            //temp set to false for status
            if(accountService.getAccountsByNumber(accountNum).getStatus()){
                SessionClient.setCardNum(fullCardNumber);
                SessionClient.setOwnBank(BinClient.CheckBIN(binNum));
                PinPage.setVisible(true);
                pinField.requestFocus();
            }
            else{
                //to be updated
                System.out.println("Fail");
                dispenseCard();
            }
        }
    }

    private void checkPin(String pin) throws IOException {
        if (PinClient.checkPin(pin)) {
            if (SessionClient.isOwnBank()) {
                Navigator.setRoot("MainDashboard");
            } else {
                SessionClient.setNavState("Withdraw");
                Navigator.setRoot("DepositWithdraw");
            }
        } else {
            loadingLabel.setVisible(false);
            LoadingPage.setVisible(false);
            if (attempts < 6) {
                attempts++;
                PinPage.setVisible(true);
                pinField.requestFocus();
                invalidPinLabel.setVisible(true);
            } else {
                // set cardNum probably can simplify
                PhoneOTPClient phoneOTP = new PhoneOTPClient();
                String accountNum = SessionClient.getCardNum().substring(6, 15);
                AccountService accountService = new AccountService();
                phoneOTP.warningOTP(accountService.getAccountsByNumber(accountNum));
                //temp set to false for status
                accountService.updateAccountStatus(accountService.getAccountsByNumber(accountNum), false);

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
        });
        transition.play();
    }
}
