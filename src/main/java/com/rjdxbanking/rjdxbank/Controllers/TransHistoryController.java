package com.rjdxbanking.rjdxbank.Controllers;

import com.rjdxbanking.rjdxbank.Clients.SessionClient;
import com.rjdxbanking.rjdxbank.Entity.Transaction;
import com.rjdxbanking.rjdxbank.Entity.User;
import com.rjdxbanking.rjdxbank.Helpers.Navigator;
import com.rjdxbanking.rjdxbank.Services.TransactionService;
import com.rjdxbanking.rjdxbank.Services.UserService;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class TransHistoryController implements Initializable {

        @FXML
        private Label balLabel;

        @FXML
        private Label accLabel;

        @FXML
        private Label interestRateLabel;

        @FXML
        private Button btnChinese;

        @FXML
        private Button btnEnglish;

        @FXML
        private Button btnMalay;

        @FXML
        private ImageView iconPrimary;

        @FXML
        private Label nameLabel;

        @FXML
        private TableView<Transaction> transHistoryTable;

        TransactionService tranService = new TransactionService();

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

        @Override
        public void initialize(URL location, ResourceBundle resources) {
                Path iconPrimaryPath = FileSystems.getDefault().getPath(
                                "src/main/resources/com/rjdxbanking/rjdxbank/Images/",
                                "WhiteIconPrimary.png");
                Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
                iconPrimary.setImage(iconPrimaryImage);

                UserService userService = new UserService();
                User currentUser = userService.getUserByUserId(SessionClient.getAccount().getUserId());
                nameLabel.setText(" " + currentUser.getFullName());
                accLabel.setText(SessionClient.getAccount().getAccountType() + ": ");
                balLabel.setText(String.valueOf(SessionClient.getAccount().getAvailableBalance()));
                interestRateLabel.setText(String.valueOf(SessionClient.getAccount().getYearlyProjectedInterestRate()));
                intializeTable();
        }

        void intializeTable() {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                ObservableList<Transaction> listTrans = tranService
                                .getTransactionsByAccountIdLimit100(SessionClient.account.getId());

                // System.out.println(listTrans.get(0).getId());

                TableColumn<Transaction, LocalDateTime> transTimeCol = new TableColumn<Transaction, LocalDateTime>(
                                "Transaction Time");
                transTimeCol.setMinWidth(200);
                transTimeCol.setCellValueFactory(
                                new PropertyValueFactory<Transaction, LocalDateTime>("timeStamp"));
                transTimeCol.setCellFactory(col -> new TableCell<Transaction, LocalDateTime>() {
                        @Override
                        protected void updateItem(LocalDateTime item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty)
                                        setText(null);
                                else
                                        setText(dtf.format(item).toString());
                        }
                });
                transTimeCol.setSortType(TableColumn.SortType.DESCENDING);

                TableColumn<Transaction, String> transactionTypeCol = new TableColumn<Transaction, String>(
                                "Transaction Type");
                transactionTypeCol.setMinWidth(200);
                transactionTypeCol.setCellValueFactory(
                                new PropertyValueFactory<Transaction, String>("transactionType"));

                TableColumn<Transaction, String> currencyCodeCol = new TableColumn<Transaction, String>(
                                "Currency Code");
                currencyCodeCol.setMinWidth(200);
                currencyCodeCol.setCellValueFactory(
                                new PropertyValueFactory<Transaction, String>("currencyCode"));

                TableColumn<Transaction, Integer> transactionAmountCol = new TableColumn<Transaction, Integer>(
                                "Transaction Amount");
                transactionAmountCol.setMinWidth(200);
                transactionAmountCol.setCellValueFactory(
                                new PropertyValueFactory<Transaction, Integer>("transactionAmount"));

                TableColumn<Transaction, String> statusCol = new TableColumn<Transaction, String>("Status");
                statusCol.setMinWidth(180);
                statusCol.setCellValueFactory(
                                new PropertyValueFactory<Transaction, String>("transactionStatus"));
                if (listTrans.size() == 0) {

                } else {
                        transHistoryTable.setItems(listTrans);
                }
                transHistoryTable.getColumns().addAll(
                                transTimeCol, transactionTypeCol, currencyCodeCol,
                                transactionAmountCol, statusCol);
                transHistoryTable.getSortOrder().add(transTimeCol);
                transHistoryTable.sort();

        }

        @FXML
        void btnBackPressed(ActionEvent event) throws IOException {
                Navigator.setRoot("MainDashboard");
        }
}
