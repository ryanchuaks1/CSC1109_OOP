package com.rjdxbanking.rjdxbank.Controllers;

import java.nio.file.FileSystems;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;

import com.rjdxbanking.rjdxbank.Clients.SessionClient;
import com.rjdxbanking.rjdxbank.Entity.Account;
import com.rjdxbanking.rjdxbank.Helpers.Navigator;
import com.rjdxbanking.rjdxbank.Models.TransactionType;
import com.rjdxbanking.rjdxbank.Services.PDFService;

public class TransferController implements Initializable {

    @FXML
    private TextField accountIDField;

    @FXML
    private Button btnChinese;

    @FXML
    private Button btnEnglish;

    @FXML
    private Button btnMalay;

    @FXML
    private ImageView iconPrimary;

    @FXML
    private AnchorPane transferPane;

    @FXML
    private TextField transferTextField;

    private Double amountInCashCompartment = 0.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amountInCashCompartment = 0.0;
        Path iconPrimaryPath = FileSystems.getDefault().getPath(
                "src/main/resources/com/rjdxbanking/rjdxbank/Images/", "WhiteIconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);

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
    private void confirmTransferPressed(ActionEvent event) throws FileNotFoundException, IOException {
        Account account = SessionClient.getAccount();
        account.Transfer(amountInCashCompartment, accountIDField.getText());
        PDFService.Receipt(account, TransactionType.OverseasTransfer, String.valueOf(amountInCashCompartment));
        Navigator.logout();
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
