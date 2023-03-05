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

    @PropertyName("transactionAmount")
    private double transactionAmount;

    @PropertyName("transactionType")
    private TransactionType transactionType;

    @PropertyName("transactionStatus")
    private TransactionStatus transactionStatus;

    @PropertyName("accountId")
    private String AccountId;

    @PropertyName("from")
    private String from;

    @PropertyName("to")
    private String to;


    public String getId() {
        return Id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public double getTransactionAmount() {
        return transactionAmount;
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

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = Models.TransactionStatus.valueOf(transactionStatus);
    }

    public String getAccountId() {
        return this.AccountId;
    }
}
