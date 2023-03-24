package com.rjdxbanking.rjdxbank.Models;

public class Balance {
    private double availableBalance;
    private double pendingBalance;

    public double getAvailableBalance()
    {
        return this.availableBalance;
    }

    public double getPendingBalance() {
        return this.pendingBalance;
    }

    public void addToAvailableBalance(double amount) {
        this.availableBalance += amount;
    }

    public void deductFromAvailableBalance(double amount) {
        this.availableBalance -= amount;
    }

    public void addToPendingBalance(double amount) {
        this.pendingBalance -= amount;
    }

    public void deductFromPendingeBalance(double amount) {
        this.pendingBalance -= amount;
    }
}
