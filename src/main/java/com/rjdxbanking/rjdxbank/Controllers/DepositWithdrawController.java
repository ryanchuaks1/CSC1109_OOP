package com.rjdxbanking.rjdxbank.Controllers;

import com.rjdxbanking.rjdxbank.Clients.ATMClient;
import com.rjdxbanking.rjdxbank.Clients.EmailClient;
import com.rjdxbanking.rjdxbank.Clients.SessionClient;
import com.rjdxbanking.rjdxbank.Entity.Account;
import com.rjdxbanking.rjdxbank.Exception.BillsNotEnoughException;
import com.rjdxbanking.rjdxbank.Exception.InsufficientFundsException;
import com.rjdxbanking.rjdxbank.Exception.TransferLimitExceededException;
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
    private Label limitAmountLabel;

    @FXML
    private AnchorPane depositPane;

    @FXML
    private ImageView iconPrimary;

    @FXML
    private AnchorPane insufficientFundsPane;

    @FXML
    private AnchorPane limitReachedPane;

    @FXML
    private AnchorPane invalidAmountPane;

    @FXML
    private AnchorPane withdrawPane;

    @FXML
    private TextField withdrawTextField;

    private Double amountInCashCompartment = 0.0;
    private Double limit = 0.0;

    TransactionService ts = new TransactionService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amountInCashCompartment = 0.0;
        Path iconPrimaryPath = FileSystems.getDefault().getPath(
                "src/main/resources/com/rjdxbanking/rjdxbank/Images/", "WhiteIconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);

        closePressed(null);
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
                limit = SessionClient.account.getCurrentLimit(TransactionType.Withdrawal);
                System.out.println("Withdrawal limit is " + limit);
                withdrawPane.setVisible(true);
                break;
            default:
                try {
                    Navigator.setRoot("MainDashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    // if user click on cancel, return back to mainDashboard
    @FXML
    private void cancelPressed(ActionEvent event) throws IOException {
        Navigator.setRoot("MainDashboard");
    }

    // User confirm depositPressed
    @FXML
    private void confirmDepositPressed(ActionEvent event) throws FileNotFoundException, IOException {
        Account account = SessionClient.getAccount();
        account.Deposit(amountInCashCompartment);
        PDFService.Receipt(account, TransactionType.Deposit, String.valueOf(amountInCashCompartment));
        Navigator.logout();
    }

    // Perform check on the amount inputted else, check the withdraw cash
    private void confirmWithdrawPressed(Double amount) throws FileNotFoundException, IOException {
        Account account = SessionClient.getAccount();
        if (!(amount % 10 == 0 && amount >= 20)) { // show invalid if amount not modulo by $10, and amount < 20
            invalidAmountPane.setVisible(true);
        } else { // run when amount is valid and modulo by $10, update withdraw cash in DB, and
                 // decrease the amount in user account, and PDF print out a receipt.
            try {
                ATMClient atmClient = new ATMClient();
                atmClient.checkChange(amount.intValue());
                account.Withdraw(amount);
                atmClient.withdrawCash(amount.intValue());
                PDFService.Receipt(account, TransactionType.Withdrawal, String.valueOf(withdrawTextField.getText()));
                Navigator.logout();
            } catch (InsufficientFundsException e) { // if user account do not have sufficient Funds
                insufficientFundsPane.setVisible(true);
            } catch (TransferLimitExceededException e) { // if user has hit the limit of ATMwithdrawal
                limitReachedPane.setVisible(true);
                limitAmountLabel.setText(limitAmountLabel.getText() + " " + e.getLimit());
            } catch (BillsNotEnoughException e) { //  if ATM bill count do not have enough, send an email to our email to update
                billsInsufficientPane.setVisible(true);
                EmailClient.emailUpdate();
            }
        }
    }

    @FXML
    private void numPadTickClicked(ActionEvent event) throws FileNotFoundException, IOException {
        if (withdrawTextField.getLength() != 0) {
            Double amount = Double.parseDouble(withdrawTextField.getText());
            confirmWithdrawPressed(amount);
        }
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
        if (withdrawTextField.getLength() != 0) {
            withdrawTextField.deleteText(withdrawTextField.getLength() - 1, withdrawTextField.getLength());
        }
    }

    @FXML
    private void closePressed(ActionEvent event) {
        insufficientFundsPane.setVisible(false);
        billsInsufficientPane.setVisible(false);
        limitReachedPane.setVisible(false);
        invalidAmountPane.setVisible(false);
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
