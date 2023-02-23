package Entity;

import Models.TransactionStatus;
import Models.TransactionType;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

public class Transaction {
    @DocumentId
    private String Id;

    @PropertyName("currencyCode")
    private String currencyCode;

    @PropertyName("amount")
    private double amount;

    @PropertyName("transactionType")
    private TransactionType transactionType;

    @PropertyName("transactionStatus")
    private TransactionStatus transactionStatus;

    @PropertyName("accountId")
    private String AccountId;



    public String getId() {
        return Id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return this.transactionType.name();
    }

    public void setTransactionType(String transactionType)
    {
        this.transactionType = Models.TransactionType.valueOf(transactionType);
    }

    public String getTransactionStatus() {
        return this.transactionStatus.name();
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = Models.TransactionStatus.valueOf(transactionStatus);
    }

    public String getAccountId() {
        return this.AccountId;
    }
}
