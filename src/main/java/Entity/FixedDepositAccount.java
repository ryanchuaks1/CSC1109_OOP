package Entity;

import Exceptions.AccountNotSufficientException;

public class FixedDepositAccount extends Account{
    @Override
    public double getYearlyProjectedInterestRate() {
        // If balance is lesser than minimum deposit amount account reduce interestRate
        if (getAvailableBalance() < 5000) {
            return (getAvailableBalance() * 0.01);
        }
        double interestRate = 0.04;
        return (getAvailableBalance() * interestRate);
    }
}
