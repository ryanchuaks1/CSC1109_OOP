package com.rjdxbanking.rjdxbank.Entity;

public class SavingsAccount extends Account{
    @Override
    public double getYearlyProjectedInterestRate() {
        double interestRate = 0.004;
        return (getBalance().getAvailableBalance() * interestRate);
    }
}
