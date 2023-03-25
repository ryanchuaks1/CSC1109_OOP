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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
    private Pane exitPane;

    @FXML
    private ImageView iconChangePin;

    @FXML
    private ImageView iconExit1;

    @FXML
    private ImageView iconLimit;

    @FXML
    private ImageView iconPrimary;

    @FXML
    private Pane localTransfer;

    @FXML
    private Label nameLabel;

    @FXML
    private Pane overseasTransfer;

    @FXML
    private Label rateLabel;

    @FXML
    private Label fxRateLabel;

    @FXML
    private VBox transferBox;

    @FXML
    private AnchorPane transferPane;

    @FXML
    private TextField transferTextField;

    double localLimit = 0.0;
    double overseasLimit = 0.0;
    TransactionType transType = null;
    BankService bankService = new BankService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path iconPrimaryPath = FileSystems.getDefault().getPath(
                "src/main/resources/com/rjdxbanking/rjdxbank/Images/", "WhiteIconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);
        transferBox.setVisible(true);
        localLimit = SessionClient.account.getCurrentLimit(TransactionType.LocalTransfer);
        overseasLimit = SessionClient.account.getCurrentLimit(TransactionType.OverseasTransfer);
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
    private void onMouseNavigate(MouseEvent event) throws IOException {
        if (event.getSource() == exitPane) {
            Navigator.setRoot("MainDashboard");
        } else if (event.getSource() == localTransfer) {
            List<Bank> banks = bankService.getBanks();
            for (Bank bank : banks) {
                if (bank.getIsLocal()) {
                    bankComboBox.getItems().add(bank.getBankName());
                }
            }
            transType = TransactionType.LocalTransfer;
            transferBox.setVisible(false);
            fxRateLabel.setVisible(false);
            rateLabel.setVisible(false);
        } else if (event.getSource() == overseasTransfer) {
            List<Bank> banks = bankService.getBanks();
            for (Bank bank : banks) {
                if (!bank.getIsLocal()) {
                    bankComboBox.getItems().add(bank.getBankName());
                }
            }
            transType = TransactionType.OverseasTransfer;
            transferBox.setVisible(false);
            fxRateLabel.setVisible(true);
            rateLabel.setVisible(true);
        }
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
