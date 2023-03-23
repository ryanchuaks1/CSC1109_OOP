package com.rjdxbanking.rjdxbank;

import java.io.IOException;
import java.util.List;

import com.google.firebase.FirebaseApp;
import com.rjdxbanking.rjdxbank.Clients.ATMClient;
import com.rjdxbanking.rjdxbank.Clients.EmailClient;
import com.rjdxbanking.rjdxbank.Entity.ATMChange;
import com.rjdxbanking.rjdxbank.Entity.ATMChange;
import com.rjdxbanking.rjdxbank.Entity.Account;
import com.rjdxbanking.rjdxbank.Entity.Transaction;
import com.rjdxbanking.rjdxbank.Exception.BillsNotEnoughException;
import com.rjdxbanking.rjdxbank.Helpers.FirebaseInitialize;
import com.rjdxbanking.rjdxbank.Services.ATMService;
import com.rjdxbanking.rjdxbank.Services.ATMService;
import com.rjdxbanking.rjdxbank.Services.AccountService;
import com.rjdxbanking.rjdxbank.Services.FXService;
import com.rjdxbanking.rjdxbank.Services.TransactionService;

public class Test {
    public static void main(String[] args) {

        FirebaseInitialize.initDatabase();
        // FXService s = new FXService();
        // try {
        //     System.out.println(s.fxpull());
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        // AccountService ac = new AccountService();
        // Account acc = ac.getAccountsByNumber("982143467");

        // TransactionService ts = new TransactionService();
        // List<Transaction> test = ts.getTransactionsByDate("2VdvzfhX0Wm3qB9C9at7");
        // System.out.println(test.get(0));

        // System.out.println(acc.getCurrentWithdrawalLimit());

        // EmailClient ec = new EmailClient();
        // ec.emailUpdate();

        // ATMService a = new ATMService();
        // ATMChange ac = a.getATMChange();
        // System.out.println(ac.getFiftyDollars());
        // ATMClient acli = new ATMClient();
        // try {
        //     acli.WithdrawCash(55);
        // } catch (BillsNotEnoughException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        // ATMChange after = a.getATMChange();
        // System.out.println(after.getFiftyDollars());
    }
}
