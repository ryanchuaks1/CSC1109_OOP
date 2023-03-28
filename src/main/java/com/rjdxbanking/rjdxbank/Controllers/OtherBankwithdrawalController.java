package com.rjdxbanking.rjdxbank.Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import com.rjdxbanking.rjdxbank.Clients.ATMClient;
import com.rjdxbanking.rjdxbank.Clients.EmailClient;
import com.rjdxbanking.rjdxbank.Clients.SessionClient;
import com.rjdxbanking.rjdxbank.Entity.Bank;
import com.rjdxbanking.rjdxbank.Exception.BillsNotEnoughException;
import com.rjdxbanking.rjdxbank.Helpers.Navigator;
import com.rjdxbanking.rjdxbank.Models.CreateIncomingTransaction;
import com.rjdxbanking.rjdxbank.Models.TransactionStatus;
import com.rjdxbanking.rjdxbank.Models.TransactionType;
import com.rjdxbanking.rjdxbank.Services.BankService;
import com.rjdxbanking.rjdxbank.Services.FXService;
import com.rjdxbanking.rjdxbank.Services.PDFService;
import com.rjdxbanking.rjdxbank.Services.TransactionService;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class OtherBankwithdrawalController implements Initializable {
    @FXML
    private Button btnChinese;

    @FXML
    private Button btnEnglish;

    @FXML
    private Button btnMalay;

    @FXML
    private ImageView iconPrimary;

    @FXML
    private AnchorPane withdrawPane;

    @FXML
    private AnchorPane billsInsufficientPane;

    @FXML
    private AnchorPane invalidAmountPane;

    @FXML
    private TextField withdrawTextField;

    @FXML
    private Label rateLabel;

    @FXML
    private AnchorPane successPane;

    private final TransactionService itService = new TransactionService();
    private final ATMClient atmClient = new ATMClient();
    private final BankService bankService = new BankService();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @FXML
    void setLanguage(ActionEvent event) throws IOException {
        if (event.getSource() == btnEnglish) {
            Navigator.setLocale(Locale.forLanguageTag("en"), "OtherBankWithdrawal");
        } else if (event.getSource() == btnChinese) {
            Navigator.setLocale(Locale.forLanguageTag("zh"), "OtherBankWithdrawal");
        } else if (event.getSource() == btnMalay) {
            Navigator.setLocale(Locale.forLanguageTag("ms"), "OtherBankWithdrawal");
        }
    }

    @FXML
    private void cancelPressed(ActionEvent event) throws IOException {
        Navigator.logout();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            rateLabel.setText(
                    "1 SGD = " + FXService.foreignXchange(SessionClient.currency) + " " + SessionClient.currency);
        } catch (IOException e) {
            e.printStackTrace();
        }
        invalidAmountPane.setVisible(false);
        billsInsufficientPane.setVisible(false);
        successPane.setVisible(false);
    }

    @FXML
    private void numPadBackClicked(ActionEvent event) {
        if (withdrawTextField.getLength() != 0) {
            withdrawTextField.deleteText(withdrawTextField.getLength() - 1, withdrawTextField.getLength());
        }
    }

    @FXML
    private void numPadClicked(ActionEvent event) {
        withdrawTextField.appendText(((Button) event.getSource()).getText());
    }

    @FXML
    void numPadTickClicked(ActionEvent event) throws FileNotFoundException, IOException {
        if (withdrawTextField.getLength() != 0) {
            Double amount = Double.parseDouble(withdrawTextField.getText());
            confirmWithdrawPressed(amount);
        }
    }

    // Method on confirmwithdraw button is pressed
    private void confirmWithdrawPressed(Double amountWithdrawn) throws FileNotFoundException, IOException {
        // amount withdrawal less than 20
        if (!(amountWithdrawn % 10 == 0 && amountWithdrawn >= 20)) {
            invalidAmountPane.setVisible(true);
        } else {
            // else check the current card belongs to local or overseas
            try {
                String cardNo = SessionClient.getCardNum();
                String binNum = cardNo.substring(0, 6);
                String bankNumber = cardNo.substring(6, 15);

                Bank bank = bankService.getBankByRoute(binNum);
                LocalDateTime now = LocalDateTime.now();
                double conversionValue = FXService.foreignXchange(SessionClient.getCurrency());
                double amountDebited = (bank.getIsLocal() ? amountWithdrawn : amountWithdrawn * conversionValue);

                // if ATMClient do not have enough bills left, throw a billInsufficientException
                atmClient.checkChange((int) (amountWithdrawn.intValue()));
                atmClient.withdrawCash((int) (amountWithdrawn.intValue()));

                CreateIncomingTransaction incTransaction = new CreateIncomingTransaction(dtf.format(now),
                        amountWithdrawn,
                        amountDebited,
                        SessionClient.getCurrency(),
                        bank.getBankRoute(),
                        bank.getBankName(),
                        bankNumber,
                        TransactionStatus.Pending);

                itService.createTransaction(incTransaction);

                // Successpane to show successful and log out after 2seconds
                successPane.setVisible(true);
                Duration delay = Duration.seconds(2);
                PauseTransition transition = new PauseTransition(delay);
                transition.setOnFinished(evt -> {
                    try {
                        Navigator.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                if (!bank.getIsLocal()) {
                    // for other banks that is not local
                    // create PDF
                    PDFService.otherReceipt(bank, TransactionType.Withdrawal,
                            String.valueOf(String.format("%.2f",
                                    amountWithdrawn)),
                            String.valueOf(String.format("%.2f", amountDebited)),
                            String.valueOf(conversionValue));
                } else {
                    // for local banks
                    // create local banks receipt
                    PDFService.otherReceipt(bank, TransactionType.Withdrawal,
                            String.valueOf(amountDebited));
                }
                transition.play();

            } catch (BillsNotEnoughException e) {
                billsInsufficientPane.setVisible(true);
                EmailClient.emailUpdate();
            }
        }
    }

    @FXML
    private void closePressed(ActionEvent event) {
        billsInsufficientPane.setVisible(false);
        invalidAmountPane.setVisible(false);
    }
}
