package Models;


public class Account {
    public int AccountNo;
    public double Balance;

    // Create new account
    Account() {
        AccountNo = 0; // Need to find a method for a randomized UID here
        Balance = 0;
    }

    // Get account number
    public int getAccountNo() {
        return AccountNo;
    }

    public double getBalance() {
        return Balance;
    }

    public double addBalance(double value) {
        return value + Balance;
    }

    public double subtractBalance(double value) {
        double newValue = Balance - value;
        if (newValue > 0) {
            return newValue;
        } else {
            return -1; // to decide if should return newvalue or not
        }
    }

}
