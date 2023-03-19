package com.rjdxbanking.rjdxbank.Entity;

public class SavingsAccount extends Account{
    @Override
    public double getYearlyProjectedInterestRate() {
        double interestRate = 0.04;
        return (getAvailableBalance() * interestRate);
    }
}
