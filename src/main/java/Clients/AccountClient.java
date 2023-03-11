package Clients;

import Entity.Account;
import Entity.User;
import Services.AccountService;
import com.password4j.Password;

public class AccountClient {
    final AccountService accountService = new AccountService();
    public Account Login(String creditCardNo, String unhashedPassword)
    {
        Account account = null;
        account = accountService.getAccountsByCreditCard(creditCardNo);
        if (Password.check(unhashedPassword, account.getPinNo()).withArgon2()) {
            return account;
        }
        return null;
    }
}