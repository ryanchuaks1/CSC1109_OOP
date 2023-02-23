package Entity;

import Models.CreateTransaction;
import Models.TransactionStatus;
import Models.TransactionType;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import com.google.firebase.cloud.FirestoreClient;

import java.util.List;

public class Account {
    @DocumentId
    private String Id;

    @PropertyName("userId")
    private String userId;

    @PropertyName("localTransferLimit")
    private double localTransferLimit;

    @PropertyName("internationalTransferLimit")
    private double internationalTransferLimit;

    Firestore db = FirestoreClient.getFirestore();

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

    // TODO: Develop algorithm to determine if accountNo is international
    // TODO: International Transfer should always be pending.
    public void InternationalTransfer(double amount, String accountNo)
    {
    }

    //TODO: Reminder that there will be 2 transactions inserted.
    //TODO: Both transferee and transferor should have transaction logs.
    //TODO: Check for valid accountNo
    public void Transfer(double amount, String accountNo)
    {
        try {
            CreateTransaction transferorTransaction = new CreateTransaction(amount,
                    "SGD",
                    TransactionType.InternalTransfer,
                    TransactionStatus.Completed,
                    this.Id);

            CreateTransaction transfereeTransaction = new CreateTransaction(amount,
                    "SGD",
                    TransactionType.Deposit,
                    TransactionStatus.Completed,
                    accountNo);

            var apiTransferorFuture = db.collection("transactions").document().set(transferorTransaction).get();
            var apiTransfereeFuture = db.collection("transactions").document().set(transfereeTransaction).get();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void Deposit(double amount)
    {
    }

    public void Withdraw(double amount)
    {
    }

    public List<Transaction> getTransactions()
    {
        return null;
    }
}
