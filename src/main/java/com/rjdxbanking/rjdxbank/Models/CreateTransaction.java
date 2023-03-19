package com.rjdxbanking.rjdxbank.Models;

public class CreateTransaction {
    private double transactionAmount;
    private String currencyCode;
    private String to;
    private String from;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private String accountId;

    public CreateTransaction(double transactionAmount, String currencyCode, TransactionType transactionType,
                             TransactionStatus transactionStatus, String accountId) {
        this.transactionAmount = transactionAmount;
        this.currencyCode = currencyCode;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        this.accountId = accountId;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public String getAccountId() {
        return accountId;
    }
}
