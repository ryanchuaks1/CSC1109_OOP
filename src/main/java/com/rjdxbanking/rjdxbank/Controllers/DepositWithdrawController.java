package com.rjdxbanking.rjdxbank.Controllers;

import com.rjdxbanking.rjdxbank.Clients.SessionClient;
import com.rjdxbanking.rjdxbank.Entity.Account;
import com.rjdxbanking.rjdxbank.Exception.InsufficientFundsException;
import com.rjdxbanking.rjdxbank.Helpers.Navigator;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;

public class DepositWithdrawController implements Initializable {
    @FXML
    private AnchorPane actionPane;

    @FXML
    private Button btnChinese;

    @FXML
    private Button btnEnglish;

    @FXML
    private Button btnMalay;

    @FXML
    private Label depositAmountLabel;

    @FXML
    private AnchorPane depositPane;

    @FXML
    private ImageView iconPrimary;

    @FXML
    private AnchorPane withdrawPane;

    @FXML
    private AnchorPane insufficientFundsPane;

    @FXML
    private TextField withdrawTextField;

    private Double amountInCashCompartment = 0.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amountInCashCompartment = 0.0;
        Path iconPrimaryPath = FileSystems.getDefault().getPath(
                "src/main/resources/com/rjdxbanking/rjdxbank/Images/", "WhiteIconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);

        if (SessionClient.getNavState().equals("Deposit")) {
            withdrawPane.setVisible(false);
            depositPane.setVisible(false);
            actionPane.setVisible(true);
            // Delay for cash compartment opening
            Duration delay = Duration.seconds(2);
            PauseTransition transition = new PauseTransition(delay);
            transition.setOnFinished(evt -> {
                actionPane.setVisible(false);
                depositPane.setVisible(true);
            });
            transition.play();
        }
        if (SessionClient.getNavState().equals("Withdraw")) {
            depositPane.setVisible(false);
            withdrawPane.setVisible(true);
        }
    }

    @FXML
    void setLanguage(ActionEvent event) throws IOException {
        if (event.getSource() == btnEnglish) {
            Navigator.setLocale(Locale.forLanguageTag("en"), "DepositWithdraw");
        } else if (event.getSource() == btnChinese) {
            Navigator.setLocale(Locale.forLanguageTag("zh"), "DepositWithdraw");
        } else if (event.getSource() == btnMalay) {
            Navigator.setLocale(Locale.forLanguageTag("ms"), "DepositWithdraw");
        }
    }

    @FXML
    private void cancelPressed(ActionEvent event) throws IOException {
        Navigator.logout();
    }

    @FXML
    private void confirmDepositPressed(ActionEvent event) throws FileNotFoundException, IOException {
        Account account = SessionClient.getAccount();
        // Maybe need to surround this with try/catch ?
        account.Deposit(amountInCashCompartment);
        // PDFService.Receipt(account, TransactionType.Deposit,
        // String.valueOf(amountInCashCompartment));
        Navigator.logout();
    }

    @FXML
    private void confirmWithdrawPressed(ActionEvent event) throws FileNotFoundException, IOException {
        Account account = SessionClient.getAccount();
        try {
            account.Withdraw(Double.parseDouble(withdrawTextField.getText()));
        } catch (InsufficientFundsException e) {
            insufficientFundsPane.setVisible(true);
        }
        // PDFService.Receipt(account, TransactionType.Withdrawal,
        // String.valueOf(amountInCashCompartment));
        // Navigator.logout();
    }

    @FXML
    private void quickAmountClicked(ActionEvent event) throws IOException {
        String text = ((Button) event.getSource()).getText();
        Double amount = Double.parseDouble(text.substring(1, text.length()));
        Account account = SessionClient.getAccount();
        // Maybe need to surround this with try/catch ?
        try {
            account.Withdraw(amount);
        } catch (InsufficientFundsException e) {
            System.out.println("wntfhwuig heugneugbacgbvbteuitneruivteityqeiqvtnqeyvnyiernvi");
            insufficientFundsPane.setVisible(true);
        }
        // Navigator.logout();
    }

    @FXML
    private void numPadClicked(ActionEvent event) {
        withdrawTextField.appendText(((Button) event.getSource()).getText());
    }

    @FXML
    private void numPadBackClicked(ActionEvent event) {
        withdrawTextField.deleteText(withdrawTextField.getLength() - 1, withdrawTextField.getLength());
    }
    
    @FXML
    private void closePressed(ActionEvent event) {
        insufficientFundsPane.setVisible(false);
    }

    // NOTE: Code below here is for dev purposes only
    @FXML
    private void editCash(ActionEvent event) {
        String string = ((Button) event.getSource()).getText();
        Double amount = Double.parseDouble(string);
        amountInCashCompartment += amount;
        depositAmountLabel.setText(String.valueOf(amountInCashCompartment));
    }
}
