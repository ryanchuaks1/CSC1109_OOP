package Entity;

public class SavingsAccount extends Account{
    private final double interestRate = 0.01;

    public double getYearlyProjectedInterestRate() {
        return (getAvailableBalance() * interestRate);
    }
}
