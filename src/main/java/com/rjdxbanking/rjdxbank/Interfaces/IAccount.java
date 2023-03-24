package com.rjdxbanking.rjdxbank.Interfaces;

import com.rjdxbanking.rjdxbank.Models.Balance;

public interface IAccount {
    Balance getBalance();
    double getATMWithdrawalLimit();
    double getLocalTransferLimit();
    double getInternationalTransferLimit();
}
