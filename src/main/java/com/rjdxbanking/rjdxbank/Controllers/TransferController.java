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
    private TextField transferTextField;

    double localLimit = 0.0;
    double overseasLimit = 0.0;
    TransactionType transType = null;
    BankService bankService = new BankService();
    List<Bank> banks;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path iconPrimaryPath = FileSystems.getDefault().getPath(
                "src/main/resources/com/rjdxbanking/rjdxbank/Images/", "WhiteIconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);
        localLimit = SessionClient.account.getCurrentLimit(TransactionType.LocalTransfer);
        overseasLimit = SessionClient.account.getCurrentLimit(TransactionType.OverseasTransfer);

        banks = bankService.getBanks();
        for (Bank bank : banks) {
            if (bank.isLocal()) {
                bankComboBox.getItems().add(bank.getBankName() + " - Local");
            } else {
                bankComboBox.getItems().add(bank.getBankName() + " - Overseas");
            }
        }
        fxRateLabel.setVisible(false);
        rateLabel.setVisible(false);
    }

    @FXML
    void changeTargetBank(ActionEvent event) throws IOException {
        if (bankComboBox.getValue().contains("Overseas")) {
            String[] name = bankComboBox.getValue().split("-");
            String Currency = bankService.getBankByName(name[0].substring(0, name[0].length() - 1)).getCurrencyCode();
            fxRateLabel.setVisible(true);
            rateLabel.setVisible(true);
            rateLabel.setText("1 SGD = " + FXService.foreignXchange(Currency) + " " + Currency);
        } else {
            fxRateLabel.setVisible(false);
            rateLabel.setVisible(false);
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
        Navigator.setRoot("MainDashBoard");
    }

    @FXML
    private void confirmTransferPressed(ActionEvent event) throws FileNotFoundException, IOException {
        Account account = SessionClient.getAccount();
        Double amount = Double.parseDouble(transferTextField.getText());
        AccountService accService = new AccountService();
        Account accountTo = accService.getAccountsByNumber(accountNumField.getText());

        // account not found
        if (accountTo == null) {
            System.out.println("Targtet acc not found");
        } else if (localLimit < amount) {
            System.out.println(localLimit);
            System.out.println("Transfer Limit Reached");
        } else {
            try {
                account.Transfer(amount, accountTo.getId());
                PDFService.Receipt(account, TransactionType.OverseasTransfer, String.valueOf(amount));
                Navigator.logout();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
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
