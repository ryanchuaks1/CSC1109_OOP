package Entity;

import Exceptions.AccountNotSufficientException;

public class FixedDepositAccount extends Account{
    private final double interestRate = 0.04;

    public double getYearlyProjectedInterestRate() {
        return (getAvailableBalance() * interestRate);
    }
}
