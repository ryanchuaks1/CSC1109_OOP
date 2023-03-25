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
import com.rjdxbanking.rjdxbank.Exception.InsufficientFundsException;
import com.rjdxbanking.rjdxbank.Helpers.Navigator;
import com.rjdxbanking.rjdxbank.Models.CreateIncomingTransaction;
import com.rjdxbanking.rjdxbank.Models.TransactionStatus;
import com.rjdxbanking.rjdxbank.Models.TransactionType;
import com.rjdxbanking.rjdxbank.Services.BankService;
import com.rjdxbanking.rjdxbank.Services.FXService;
import com.rjdxbanking.rjdxbank.Services.IncomingTransactionService;
import com.rjdxbanking.rjdxbank.Services.PDFService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
    private TextField withdrawTextField;

    @FXML
    private Label rateLabel;

    private final IncomingTransactionService itService= new IncomingTransactionService();
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

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            rateLabel.setText("1 SGD = "+FXService.foreignXchange(SessionClient.currency) + " " + SessionClient.currency);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    private void numPadBackClicked(ActionEvent event) {
        withdrawTextField.deleteText(withdrawTextField.getLength() - 1, withdrawTextField.getLength());
    }

    @FXML
    private void numPadClicked(ActionEvent event) {
        withdrawTextField.appendText(((Button) event.getSource()).getText());
    }

    @FXML
    void numPadTickClicked(ActionEvent event) throws FileNotFoundException, IOException {
        Double amount = Double.parseDouble(withdrawTextField.getText());
        confirmWithdrawPressed(amount);
    }

    private void confirmWithdrawPressed(Double amount) throws FileNotFoundException, IOException {
        // itService
        try {
            ATMClient atmClient = new ATMClient();
            if(SessionClient.isOwnBank() == false){
                String cardNo = SessionClient.cardNum;
                String binNum = cardNo.substring(0, 6);
                String bankNumber = cardNo.substring(6, 15);
                BankService bankService = new BankService();
                Bank bank = bankService.getBankByRoute(binNum);
                LocalDateTime now = LocalDateTime.now();
                if(!bank.getIsLocal()){
                    
                    double conversionValue = FXService.foreignXchange(SessionClient.currency);
                    atmClient.WithdrawCash((int)(amount.intValue()*conversionValue));
                    
                    CreateIncomingTransaction incTransaction = new CreateIncomingTransaction(dtf.format(now), 
                        amount.intValue()*conversionValue, 
                        SessionClient.currency, 
                        bank.getBankRoute(), 
                        bankNumber, 
                        TransactionStatus.Pending);

                    itService.createTransaction(incTransaction);
                    PDFService.localReceipt(TransactionType.Withdrawal, String.valueOf(amount.intValue()*conversionValue));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful");
                }
                else{
                    atmClient.WithdrawCash((int)(amount.intValue()));
                    
                    CreateIncomingTransaction incTransaction = new CreateIncomingTransaction(dtf.format(now), 
                        amount.intValue(), 
                        SessionClient.currency, 
                        bank.getBankRoute(), 
                        bankNumber, 
                        TransactionStatus.Pending);

                    itService.createTransaction(incTransaction);
                    PDFService.localReceipt(TransactionType.Withdrawal, String.valueOf(amount.intValue()));

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful");
                }
            }
            
            Navigator.logout();
        } catch (BillsNotEnoughException e) {
            System.out.println("Insufficient Bills");
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Insufficient Bills");
                alert.setHeaderText(null);
                alert.setContentText("Insufficient Bills");
                alert.showAndWait();
            EmailClient.emailUpdate();
        }
    }

}
