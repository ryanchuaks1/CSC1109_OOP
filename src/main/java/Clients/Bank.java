package Clients;

import Models.Account;

public class Bank {
    Account account;

    public Bank() {
    }

    public boolean IsLoggedIn()
    {
        if (this.account != null) {
            return true;
        }
        return false;
    }

    public void OpenAccount()
    {

    }

    public void CloseAccount() {
    }

    public void Transfer(int receiverBankNo, double amount) {
    }

    public void Withdraw(double amount) {
    }

    public void Deposit(double amount) {
    }

    public void GetBalance() {
    }
}