package Clients;

import java.sql.PreparedStatement;

import Exceptions.UserNotFoundException;
import Helpers.DBUtil;
import Models.Account;
import Models.User;
import jdk.jshell.spi.ExecutionControl;

public class Bank {
    Account account;

    public Bank() {
    }

    public boolean IsLoggedIn() {
        return false;
    }

    public void OpenAccount() {

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