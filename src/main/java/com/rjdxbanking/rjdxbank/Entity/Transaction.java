package com.rjdxbanking.rjdxbank.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

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

    public void setTransactionType(String transactionType) {
        this.transactionType = TransactionType.valueOf(transactionType);
    }

    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(String strTimeStamp) {
        // Convert a string to a LocalDateTime
        this.timeStamp =  LocalDateTime.parse(strTimeStamp, TIMESTAMP_FORMATTER);
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
