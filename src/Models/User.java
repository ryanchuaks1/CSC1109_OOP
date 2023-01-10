package Models;

public class User {
    public Account[] Accounts;
    public double TransferLimit;
    public double OverseasTransferLimit;

    User() {
        // Accounts = new Account();
        TransferLimit = 5000;
        OverseasTransferLimit = 1000;
    }

    public Account getAccounts() {
        for (Account account : Accounts) {
            return account;
        }
        return null;
    }

    public double getTransferLimit() {
        return TransferLimit;
    }

    public double getOverseasTransferLimit() {
        return OverseasTransferLimit;
    }

    public double setTransferLimit(double newTransferLimit) {
        return TransferLimit = newTransferLimit;
    }

    public double setOverseasTransferLimit(double newOverseasTransferLimit) {
        return OverseasTransferLimit = newOverseasTransferLimit;
    }
}
