package Entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

public class Account {
    @DocumentId
    private String Id;

    @PropertyName("userId")
    private String userId;

    @PropertyName("localTransferLimit")
    private double localTransferLimit;

    @PropertyName("internationalTransferLimit")
    private double internationalTransferLimit;

    public Account()
    {
    }

    public String getId() {
        return Id;
    }

    public String getUserId() {
        return userId;
    }

    public double getLocalTransferLimit() {
        return localTransferLimit;
    }

    public double getInternationalTransferLimit() {
        return internationalTransferLimit;
    }

    //TODO: How to differentiate between InternationalTransfer
    //TODO: How to differentiate between LocalTransfer
    public void Deposit(String userId)
    {
    }

    public void Withdraw(double amount)
    {

    }
}
