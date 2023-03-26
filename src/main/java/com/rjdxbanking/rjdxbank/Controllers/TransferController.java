package com.rjdxbanking.rjdxbank.Controllers;

import java.nio.file.FileSystems;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.rjdxbanking.rjdxbank.Clients.SessionClient;
import com.rjdxbanking.rjdxbank.Entity.Account;
import com.rjdxbanking.rjdxbank.Entity.Bank;
import com.rjdxbanking.rjdxbank.Exception.InsufficientFundsException;
import com.rjdxbanking.rjdxbank.Helpers.Navigator;
import com.rjdxbanking.rjdxbank.Models.TransactionType;
import com.rjdxbanking.rjdxbank.Services.AccountService;
import com.rjdxbanking.rjdxbank.Services.BankService;
import com.rjdxbanking.rjdxbank.Services.FXService;
import com.rjdxbanking.rjdxbank.Services.PDFService;

public class TransferController implements Initializable {

    @FXML
    private TextField accountNumField;

    @FXML
    private ComboBox<String> bankComboBox;

    @FXML
    private Button btnChinese;

    @FXML
    private Button btnEnglish;

    @FXML
    private Button btnMalay;

    @FXML
    private Label fxRateLabel;

    @FXML
    private ImageView iconPrimary;

    @FXML
    private Label rateLabel;

    @FXML
    private AnchorPane transferPane;

    @FXML
    private AnchorPane insufficientFundsPane;

    @FXML
    private AnchorPane limitReachedPane;

    @FXML
    private AnchorPane accountNotFoundPane;

    @FXML
    private TextField transferTextField;

    @FXML
    private AnchorPane accountIDPane;

    double localLimit = 0.0;
    double overseasLimit = 0.0;
    TransactionType transType = null;
    BankService bankService = new BankService();
    AccountService accService = new AccountService();
    List<Bank> banks;
    Bank targetBank = null;
    String targetBankName = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path iconPrimaryPath = FileSystems.getDefault().getPath(
                "src/main/resources/com/rjdxbanking/rjdxbank/Images/", "WhiteIconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);

        localLimit = SessionClient.account.getCurrentLimit(TransactionType.LocalTransfer);
        overseasLimit = SessionClient.account.getCurrentLimit(TransactionType.OverseasTransfer);
        System.out.println(localLimit);
        System.out.println(overseasLimit);

        banks = bankService.getBanks();
        for (Bank bank : banks) {
            if (bank.getIsLocal()) {
                bankComboBox.getItems().add(bank.getBankName() + " - Local");
            } else {
                bankComboBox.getItems().add(bank.getBankName() + " - Overseas");
            }
        }
        accountNumField.setDisable(true);
        transType = null;
        fxRateLabel.setVisible(false);
        rateLabel.setVisible(false);
        accountIDPane.setVisible(false);
    }

    @FXML
    void setLanguage(ActionEvent event) throws IOException {
        if (event.getSource() == btnEnglish) {
            Navigator.setLocale(Locale.forLanguageTag("en"), "Transfer");
        } else if (event.getSource() == btnChinese) {
            Navigator.setLocale(Locale.forLanguageTag("zh"), "Transfer");
        } else if (event.getSource() == btnMalay) {
            Navigator.setLocale(Locale.forLanguageTag("ms"), "Transfer");
        }
    }

    @FXML
    private void cancelPressed(ActionEvent event) throws IOException {
        Navigator.setRoot("MainDashBoard");
    }

    @FXML
    private void closePressed(ActionEvent event) {
        insufficientFundsPane.setVisible(false);
        limitReachedPane.setVisible(false);
        accountIDPane.setVisible(false);
    }

    @FXML
    private void changeTargetBank(ActionEvent event) throws IOException {
        String[] name = bankComboBox.getValue().split("-");
        targetBankName = name[0].substring(0, name[0].length() - 1);
        if (bankComboBox.getValue().contains("Overseas")) {
            targetBank = bankService.getBankByName(targetBankName);
            String currency = targetBank.getCurrencyCode();
            accountNumField.setDisable(false);
            fxRateLabel.setVisible(true);
            rateLabel.setVisible(true);
            rateLabel.setText("1 SGD = " + FXService.foreignXchange(currency) + " " + currency);
            transType = TransactionType.OverseasTransfer;
        } else if (bankComboBox.getValue().contains("Local")) {
            targetBank = bankService.getBankByName(targetBankName);
            accountNumField.setDisable(false);
            fxRateLabel.setVisible(false);
            rateLabel.setVisible(false);
            transType = TransactionType.LocalTransfer;
        } else {
            accountNumField.setDisable(true);
            transType = null;
        }
    }

    @FXML
    private void confirmTransferPressed(ActionEvent event) throws FileNotFoundException, IOException {
        // TODO: check if fields are empty
        Double amount = Double.parseDouble(transferTextField.getText());
        Account account = SessionClient.getAccount();

        if (transType.equals(TransactionType.LocalTransfer) && (localLimit > amount)) {
            if (targetBankName == "RJDX") { // Transfer to own bank
                Account accountTo = accService.getAccountsByNumber(accountNumField.getText());
                if (accountTo == null) {
                    accountNotFoundPane.setVisible(true);
                } else {
                    try {
                        account.internalTransfer(amount, accountTo.getId());
                        PDFService.Receipt(account, TransactionType.OverseasTransfer, String.valueOf(amount));
                        Navigator.logout();
                    } catch (InsufficientFundsException e) {
                        insufficientFundsPane.setVisible(true);
                    }
                }
            } else { // Transfer to other local banks
                if(accountNumField.getText().length() < 16 || accountNumField.getText() == ""){
                    accountIDPane.setVisible(false);
                }else{
                    try {
                        account.otherBanksTransfer(amount, transType, targetBank, accountNumField.getText());
                        // PDFService.Receipt(account, TransactionType.LocalTransfer,
                        // String.valueOf(amount));
                        Navigator.logout();
                    } catch (InsufficientFundsException e) {
                        insufficientFundsPane.setVisible(true);
                    }
                }
            }
        } else if (transType.equals(TransactionType.OverseasTransfer) && (overseasLimit > amount)) { // Transfer to
                                                                                                     // overseas
            if(accountNumField.getText().length() < 16 || accountNumField.getText() == ""){
                    accountIDPane.setVisible(false);
            }else{                                                                                         
                try {
                    account.otherBanksTransfer(amount, transType, targetBank, accountNumField.getText());
                    // PDFService.Receipt(account, TransactionType.OverseasTransfer,
                    // String.valueOf(amount));
                    Navigator.logout();
                } catch (InsufficientFundsException e) {
                    insufficientFundsPane.setVisible(true);
                }
            }
        } else {
            limitReachedPane.setVisible(true);
        }
    }

    @FXML
    private void numPadClicked(ActionEvent event) {
        transferTextField.appendText(((Button) event.getSource()).getText());
    }

    @FXML
    private void numPadBackClicked(ActionEvent event) {
        transferTextField.deleteText(transferTextField.getLength() - 1, transferTextField.getLength());
    }
    
}
