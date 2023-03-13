package Clients;

import Entity.Account;
import Entity.User;
import Models.CreateAccount;
import Services.AccountService;
import com.password4j.Password;

public class AccountClient {
    final AccountService accountService = new AccountService();

    public Account Login(String accountNumber, String unhashedPassword) throws Exception
    {
        Account account = null;
        account = accountService.getAccountsByNumber(accountNumber);
        if (Password.check(unhashedPassword, account.getPinNo()).withArgon2()) {
            return account;
        }
        throw new Exception("Invalid PIN");
    }

    public void Register(CreateAccount createAccount){
        accountService.createAccount(createAccount);
    }
}
