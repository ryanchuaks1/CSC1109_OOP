package com.rjdxbanking.rjdxbank.Interfaces;

public interface IAccount {
    double getAvailableBalance();
    double getPendingAmount();
    double getATMWithdrawalLimit();
    double getLocalTransferLimit();
    double getInternationalTransferLimit();
}
