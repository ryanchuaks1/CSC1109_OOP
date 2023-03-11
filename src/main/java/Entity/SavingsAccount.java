package Entity;

public class SavingsAccount extends Account{
    private final double interestRate = 0.01;

    @Override
    public double getYearlyProjectedInterestRate() {
        return (getAvailableBalance() * interestRate);
    }
}
