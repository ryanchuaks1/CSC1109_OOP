package com.rjdxbanking.rjdxbank.Models;

import java.util.Random;

public class CreateAccount {

    private double internationalTransferLimit;
    private double localTransferLimit;
    private String userId;
    private String accountType;
    private String AccountNumber;
    private int atmWithdrawalLimit;
    private String pinNo;

    Random rand = new Random();
    public CreateAccount(String userId, String accountType, String pinNo)
    {
        this.accountType = accountType;
        this.AccountNumber = Integer.toString(100000000 + rand.nextInt(900000000));
        this.atmWithdrawalLimit = 5000;
        this.pinNo = com.password4j.Password.hash(pinNo).addRandomSalt().withArgon2().getResult();
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

    public String getAccountType() {
        return accountType;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public int getAtmWithdrawalLimit() {
        return atmWithdrawalLimit;
    }

    public String getPinNo() {
        return pinNo;
    }
}
