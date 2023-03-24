package com.rjdxbanking.rjdxbank.Models;

public class CreateIncomingTransaction {
    private String timeStamp;
    private double transactionAmount;
    private String currencyCode;
    private String fromBankRoute;
    private String fromAccountNumber;
    private TransactionStatus transactionStatus;

    public String getTimeStamp() {
        return timeStamp;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getFromBankRoute() {
        return fromBankRoute;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }
}
