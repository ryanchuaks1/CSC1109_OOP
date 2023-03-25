package com.rjdxbanking.rjdxbank.Models;

public class CreateIncomingTransaction {
    private String timeStamp;
    private double transactionAmount;
    private double withdrawAmount;
    private String currencyCode;
    private String fromBankRoute;
    private String fromBankName;
    private String fromAccountNumber;
    private TransactionStatus transactionStatus;

    public CreateIncomingTransaction(String timeStamp, double withdrawAmount, double transactionAmount,
            String currencyCode, String fromBankRoute, String fromBankName, String fromAccountNumber,
            TransactionStatus transactionStatus) {
        this.timeStamp = timeStamp;
        this.withdrawAmount = withdrawAmount;
        this.transactionAmount = transactionAmount;
        this.currencyCode = currencyCode;
        this.fromBankRoute = fromBankRoute;
        this.fromBankName = fromBankName;
        this.fromAccountNumber = fromAccountNumber;
        this.transactionStatus = transactionStatus;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getFromBankName() {
        return fromBankName;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public double getWithdrawAmount() {
        return withdrawAmount;
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
