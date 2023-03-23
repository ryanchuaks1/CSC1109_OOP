package com.rjdxbanking.rjdxbank;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.rjdxbanking.rjdxbank.Clients.ATMClient;
import com.rjdxbanking.rjdxbank.Clients.EmailClient;
import com.rjdxbanking.rjdxbank.Entity.ATMChange;
import com.rjdxbanking.rjdxbank.Entity.ATMChange;
import com.rjdxbanking.rjdxbank.Entity.Account;
import com.rjdxbanking.rjdxbank.Entity.Transaction;
import com.rjdxbanking.rjdxbank.Exception.BillsNotEnoughException;
import com.rjdxbanking.rjdxbank.Helpers.FirebaseInitialize;
import com.rjdxbanking.rjdxbank.Models.TransactionType;
import com.rjdxbanking.rjdxbank.Services.ATMService;
import com.rjdxbanking.rjdxbank.Services.ATMService;
import com.rjdxbanking.rjdxbank.Services.AccountService;
import com.rjdxbanking.rjdxbank.Services.FXService;
import com.rjdxbanking.rjdxbank.Services.TransactionService;

public class Test {
    public static void main(String[] args) {

        FirebaseInitialize.initDatabase();

        AccountService ac = new AccountService();
        Account acc = ac.getAccountsByNumber("128263930");
        System.out.println(acc.getCurrentLimit(TransactionType.Withdrawal));
        System.out.println(acc.getATMWithdrawalLimit());
        
        // FXService s = new FXService();
        // try {
        // System.out.println(s.fxpull());
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // AccountService ac = new AccountService();
        // Account acc = ac.getAccountsByNumber("982143467");

        // System.out.println(acc.getCurrentWithdrawalLimit());

        // EmailClient ec = new EmailClient();
        // ec.emailUpdate();
    }
}
