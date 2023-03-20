package com.rjdxbanking.rjdxbank.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import com.rjdxbanking.rjdxbank.Models.TransactionStatus;
import com.rjdxbanking.rjdxbank.Models.TransactionType;

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

    @PropertyName("timeStamp")
    private LocalDateTime timeStamp;

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

    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = TransactionType.valueOf(transactionType);
    }

    public TransactionStatus getTransactionStatus() {
        return this.transactionStatus;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = TransactionStatus.valueOf(transactionStatus);
    }

    public String getAccountId() {
        return this.AccountId;
    }
}
