package Clients;

import Interfaces.IBank;

public class Bank implements IBank {
    public void CloseAccount() {
    }

    public void Transfer(String receiverBankNo, double amount) {
    }

    @Override
    public void Withdraw(double amount) {
    }
    @Override
    public void Deposit(double amount) {
    }
    @Override
    public void GetBalance() {
    }
}