package com.rjdxbanking.rjdxbank.Controllers;

import com.rjdxbanking.rjdxbank.Clients.ATMClient;
import com.rjdxbanking.rjdxbank.Clients.EmailClient;
import com.rjdxbanking.rjdxbank.Clients.SessionClient;
import com.rjdxbanking.rjdxbank.Entity.Account;
import com.rjdxbanking.rjdxbank.Exception.BillsNotEnoughException;
import com.rjdxbanking.rjdxbank.Entity.Transaction;
import com.rjdxbanking.rjdxbank.Exception.InsufficientFundsException;
import com.rjdxbanking.rjdxbank.Helpers.Navigator;
import com.rjdxbanking.rjdxbank.Models.TransactionType;
import com.rjdxbanking.rjdxbank.Services.PDFService;
import com.rjdxbanking.rjdxbank.Services.TransactionService;

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
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DepositWithdrawController implements Initializable {
    @FXML
    private AnchorPane actionPane;

    @FXML
    private AnchorPane billsInsufficientPane;

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
    private Button insufficientBtn;

    @FXML
    private AnchorPane insufficientFundsPane;

    @FXML
    private Button lackOfcashBtn;

    @FXML
    private AnchorPane withdrawPane;

    @FXML
    private TextField withdrawTextField;

    private Double amountInCashCompartment = 0.0;

    TransactionService ts = new TransactionService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amountInCashCompartment = 0.0;
        Path iconPrimaryPath = FileSystems.getDefault().getPath(
                "src/main/resources/com/rjdxbanking/rjdxbank/Images/", "WhiteIconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);
        insufficientFundsPane.setVisible(false);
        billsInsufficientPane.setVisible(false);
        withdrawPane.setVisible(false);
        depositPane.setVisible(false);

        switch (SessionClient.getNavState()) {
            case "Deposit":
                actionPane.setVisible(true);
                // Delay for cash compartment opening
                Duration delay = Duration.seconds(2);
                PauseTransition transition = new PauseTransition(delay);
                transition.setOnFinished(evt -> {
                    actionPane.setVisible(false);
                    depositPane.setVisible(true);
                });
                transition.play();
                break;
            case "Withdraw":
                withdrawPane.setVisible(true);
                break;
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
        if (SessionClient.isOwnBank()) {
            Navigator.setRoot("MainDashBoard");
        } else {
            Navigator.logout();
        }
    }

    @FXML
    private void confirmDepositPressed(ActionEvent event) throws FileNotFoundException, IOException {
        Account account = SessionClient.getAccount();
        account.Deposit(amountInCashCompartment);
        PDFService.Receipt(account, TransactionType.Deposit, String.valueOf(amountInCashCompartment));
        Navigator.logout();
    }

    private void confirmWithdrawPressed(Double amount) throws FileNotFoundException, IOException {
        EmailClient eClient = new EmailClient();
        if (SessionClient.isOwnBank()) {
            Account account = SessionClient.getAccount();
            ATMClient aClient = new ATMClient();
            try {
                aClient.WithdrawCash(amount.intValue());
                account.Withdraw(amount);
                PDFService.Receipt(account, TransactionType.Withdrawal, String.valueOf(withdrawTextField));
                Navigator.logout();
            } catch (InsufficientFundsException e) {
                insufficientFundsPane.setVisible(true);
            } catch (BillsNotEnoughException e) {
                // TODO Maybe another pane to tell not enough money left in ATM
                eClient.emailUpdate();
                billsInsufficientPane.setVisible(true);
            }
        }
        else {
            // PDFService.Receipt(account, TransactionType.Withdrawal, String.valueOf(withdrawTextField)); 
            Navigator.logout();
        }
    }

    @FXML
    private void numPadTickClicked(ActionEvent event) throws FileNotFoundException, IOException {
        Double amount = Double.parseDouble(withdrawTextField.getText());
        confirmWithdrawPressed(amount);
    }

    @FXML
    private void quickAmountClicked(ActionEvent event) throws IOException {
        String text = ((Button) event.getSource()).getText();
        Double amount = Double.parseDouble(text.substring(1, text.length()));
        confirmWithdrawPressed(amount);
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
        if (event.getSource() == insufficientBtn) {
            insufficientFundsPane.setVisible(false);
        } else if (event.getSource() == lackOfcashBtn) {
            billsInsufficientPane.setVisible(false);
        }
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
