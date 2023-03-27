package com.rjdxbanking.rjdxbank.Entity;

public class FixedDepositAccount extends Account{
    @Override
    public double getYearlyProjectedInterestRate() {
        // If balance is lesser than minimum deposit amount account reduce interestRate
        if (getBalance().getAvailableBalance() < 5000) {
            return (getBalance().getAvailableBalance() * 0.004);
        }
        double interestRate = 0.01;
        return (getBalance().getAvailableBalance() * interestRate);
    }
}
