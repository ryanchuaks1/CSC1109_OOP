package Models;

public class CreateAccount {
    private double internationalTransferLimit;
    private double localTransferLimit;
    private String userId;

    public CreateAccount(String userId)
    {
        this.internationalTransferLimit = 5000;
        this.localTransferLimit = 5000;
        this.userId = userId;
    }

    public double getInternationalTransferLimit() {
        return internationalTransferLimit;
    }

    public double getLocalTransferLimit() {
        return localTransferLimit;
    }

    public String getUserId() {
        return userId;
    }
}
