package com.rjdxbanking.rjdxbank.Controllers;

import com.password4j.Password;
import com.rjdxbanking.rjdxbank.Clients.PhoneOTPClient;
import com.rjdxbanking.rjdxbank.Clients.SessionClient;
import com.rjdxbanking.rjdxbank.Entity.Account;
import com.rjdxbanking.rjdxbank.Entity.User;
import com.rjdxbanking.rjdxbank.Helpers.Navigator;
import com.rjdxbanking.rjdxbank.Services.AccountService;
import com.rjdxbanking.rjdxbank.Services.UserService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private Button btnChinese;

    @FXML
    private Button btnEnglish;

    @FXML
    private Button btnMalay;

    @FXML
    private Button changeLimitBtn;

    @FXML
    private AnchorPane changePane;

    @FXML
    private Button changePin;

    @FXML
    private Pane changePinPane;

    @FXML
    private PasswordField confirmNewpin;

    @FXML
    private Pane exitPane;

    @FXML
    private ImageView iconChangePin;

    @FXML
    private ImageView iconExit;

    @FXML
    private ImageView iconLimit;

    @FXML
    private ImageView iconPrimary;

    @FXML
    private Pane limitPane;

    @FXML
    private AnchorPane limitSettingsPane;

    @FXML
    private ComboBox<Double> atmWithdrawalLimit;

    @FXML
    private ComboBox<Double> internationalTransferLimit;

    @FXML
    private ComboBox<Double> localTransferLimit;

    @FXML
    private Label nameLabel;

    @FXML
    private PasswordField newPin;

    @FXML
    private PasswordField oldPin;

    @FXML
    private Button reOTPBtn;

    @FXML
    private Button returnBackBtn;

    @FXML
    private Button returnBackBtn1;

    @FXML
    private VBox settingBox;

    @FXML
    private Button verificationBtn;

    @FXML
    private PasswordField verificationOTP;

    @FXML
    private AnchorPane verificationPane;

    PhoneOTPClient otpClient = new PhoneOTPClient();
    AccountService aService = new AccountService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path iconPrimaryPath = FileSystems.getDefault().getPath(
                "src/main/resources/com/rjdxbanking/rjdxbank/Images/", "WhiteIconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);

        Path iconDepositPath = FileSystems.getDefault().getPath(
                "src/main/resources/com/rjdxbanking/rjdxbank/Images/", "DepositIcon.png");
        Image iconDepositImage = new Image(iconDepositPath.toUri().toString());
        iconChangePin.setImage(iconDepositImage);

        Path iconTransferPath = FileSystems.getDefault().getPath(
                "src/main/resources/com/rjdxbanking/rjdxbank/Images/", "TransferIcon.png");
        Image iconTransferImage = new Image(iconTransferPath.toUri().toString());
        iconLimit.setImage(iconTransferImage);

        Path iconExitPath = FileSystems.getDefault().getPath(
                "src/main/resources/com/rjdxbanking/rjdxbank/Images/", "ExitIcon.png");
        Image iconExitImage = new Image(iconExitPath.toUri().toString());
        iconExit.setImage(iconExitImage);

        UserService userService = new UserService();
        User currentUser = userService.getUserByUserId(SessionClient.getAccount().getUserId());
        nameLabel.setText(" " + currentUser.getFullName());

        changePane.setVisible(false);
        verificationPane.setVisible(false);
        limitSettingsPane.setVisible(false);

        addTextLimiter(confirmNewpin, 6);
        addTextLimiter(oldPin, 6);
        addTextLimiter(newPin, 6);

        localTransferLimit.getItems().addAll(0.00, 500.00, 1000.00, 2000.00, 3000.00, 5000.00, 7000.00, 9000.00);
        localTransferLimit.setValue(SessionClient.getAccount().getATMWithdrawalLimit());
        atmWithdrawalLimit.getItems().addAll(0.00, 500.00, 1000.00, 2000.00, 3000.00, 5000.00, 7000.00, 9000.00);
        atmWithdrawalLimit.setValue(SessionClient.getAccount().getATMWithdrawalLimit());
        internationalTransferLimit.getItems().addAll(0.00, 500.00, 1000.00, 2000.00, 3000.00, 5000.00, 7000.00,
                9000.00);
        internationalTransferLimit.setValue(SessionClient.getAccount().getATMWithdrawalLimit());
        // localTransferLimit.getItems(String.format("%.2f",
        // essionClient.getAccount().getLocalTransferLimit()));
        // atmWithdrawalLimit.setText(String.format("%.2f",
        // SessionClient.getAccount().getATMWithdrawalLimit()));
        // internationalTransferLimit
        // .setText(String.format("%.2f",
        // SessionClient.getAccount().getInternationalTransferLimit()));
    }

    @FXML
    void onButtonPress(ActionEvent event) {
        if (event.getSource() == changePin) {
            changePinMethod();
        } else if (event.getSource() == changeLimitBtn) {
            changeLimitMethod();
        } else if (event.getSource() == verificationBtn) {
            // verification
            if (otpClient.verification(verificationOTP.getText())) {
                verificationPane.setVisible(false);
                changePane.setVisible(true);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("VerificationOTP wrong");
                alert.setHeaderText(null);
                alert.setContentText("Wrong OTP");
                alert.showAndWait();
            }
        } else if (event.getSource() == reOTPBtn) {
            otpClient.phoneOTP(SessionClient.getAccount());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("OTP resend");
            alert.setHeaderText(null);
            alert.setContentText("OTP resend to your phone");
            alert.showAndWait();
        } else if (event.getSource() == returnBackBtn) {
            verificationPane.setVisible(false);
            settingBox.setVisible(true);
        } else if (event.getSource() == returnBackBtn1) {
            limitSettingsPane.setVisible(false);
            settingBox.setVisible(true);
        }
    }

    public void changePinMethod() {
        String oldpin = oldPin.getText();
        String newpin = newPin.getText();
        String confirmNewin = newPin.getText();

        Account currentAcc = SessionClient.getAccount();
        AccountService accService = new AccountService();
        // check if old is correct
        if (Password.check(oldpin, currentAcc.getPinNo()).withArgon2()) {
            // check if new pin is same as old pin, if it is not the same perform change of
            // pin
            if (!Password.check(newpin, currentAcc.getPinNo()).withArgon2()) {
                if (newpin.equals(confirmNewin)) {
                    newpin = com.password4j.Password.hash(newpin).addRandomSalt().withArgon2().getResult();
                    accService.changePin(currentAcc, newpin);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Pin Updated");
                    alert.setHeaderText(null);
                    alert.setContentText("Pin Updated, please login with your new pin");
                    alert.showAndWait();
                    try {
                        Navigator.logout();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("New pin and confirm new pin are not the same");
                    alert.setHeaderText(null);
                    alert.setContentText("New pin and confirm New Pin are not the same");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Pin cannot be the same as old pin");
                alert.setHeaderText(null);
                alert.setContentText("Pin cannot be the same as old pin");
                alert.showAndWait();
            }
        }
    }

    public void changeLimitMethod() {
        try {
            aService.updateAccountLimits(SessionClient.getAccount(), "localTransferLimit",
                    localTransferLimit.getValue());
            aService.updateAccountLimits(SessionClient.getAccount(), "internationalTransferLimit",
                    internationalTransferLimit.getValue());
            aService.updateAccountLimits(SessionClient.getAccount(), "atmWithdrawalLimit",
                    atmWithdrawalLimit.getValue());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Limits Updated");
            alert.setHeaderText(null);
            alert.setContentText("Limits Updated, please reinsert card");
            alert.showAndWait();
            try {
                Navigator.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }

    public static void addTextLimiter(final PasswordField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue,
                    final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                } else if (!tf.getText().matches("\\d*") & tf.getText().length() < maxLength) {
                    tf.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private void onMouseNavigate(MouseEvent event) throws IOException {
        if (event.getSource() == changePinPane) {
            settingBox.setVisible(false);
            verificationPane.setVisible(true);
            otpClient.phoneOTP(SessionClient.getAccount());
        } else if (event.getSource() == limitPane) {
            settingBox.setVisible(false);
            limitSettingsPane.setVisible(true);
        } else if (event.getSource() == exitPane) {
            Navigator.setRoot("MainDashBoard");
        }
    }

    @FXML
    void setLanguage(ActionEvent event) throws IOException {
        if (event.getSource() == btnEnglish) {
            Navigator.setLocale(Locale.forLanguageTag("en"), "MainDashboard");
        } else if (event.getSource() == btnChinese) {
            Navigator.setLocale(Locale.forLanguageTag("zh"), "MainDashboard");
        } else if (event.getSource() == btnMalay) {
            Navigator.setLocale(Locale.forLanguageTag("ms"), "MainDashboard");
        }
    }

}
