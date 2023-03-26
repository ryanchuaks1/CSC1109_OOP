package com.rjdxbanking.rjdxbank.Clients;

import com.password4j.Password;
import com.rjdxbanking.rjdxbank.Entity.Account;
import com.rjdxbanking.rjdxbank.Models.CreateAccount;
import com.rjdxbanking.rjdxbank.Services.AccountService;

public class AccountClient {
    final AccountService accountService = new AccountService();

    //for login
    public Account Login(String accountNumber, String unhashedPassword) throws Exception
    {
        Account account = null;
        account = accountService.getAccountsByNumber(accountNumber);
        if (Password.check(unhashedPassword, account.getPinNo()).withArgon2()) {
            return account;
        }
        throw new Exception("Invalid PIN");
    }

    //not used current for development purposes
    public void Register(CreateAccount createAccount){
        accountService.createAccount(createAccount);
    }
}
