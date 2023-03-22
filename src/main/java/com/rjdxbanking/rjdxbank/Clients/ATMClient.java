package com.rjdxbanking.rjdxbank.Clients;

import com.rjdxbanking.rjdxbank.Exception.BillsNotEnoughException;
import com.rjdxbanking.rjdxbank.Models.ATMChange;

public class ATMClient {
    // The private values here determine how much cash count we have.
    private int twoDollars;
    private int fiveDollars;
    private int tenDollars;
    private int fiftyDollars;
    private int hundredDollars;

    public ATMClient()
    {
        this.twoDollars = 4;
        this.fiveDollars = 4;
        this.tenDollars = 0;
        this.fiftyDollars = 0;
        this.hundredDollars = 0;
    }

    public ATMChange WithdrawCash(int dollars) throws BillsNotEnoughException {
        // Calculate the number of bills of each denomination needed to make up the withdrawal amount
        int hundredCount = Math.min(hundredDollars, dollars / 100);
        dollars -= (hundredCount * 100);

        int fiftyCount = Math.min(fiftyDollars, dollars / 50);
        dollars -= (fiftyCount * 50);

        int tenCount = Math.min(tenDollars, dollars / 10);
        dollars -= (tenCount * 10);

        int fiveCount = Math.min(fiveDollars, dollars / 5);
        dollars -= (fiveCount * 5);

        int twoCount = Math.min(twoDollars, dollars / 2);
        dollars -= (twoCount * 2);

        // If there is still an amount remaining, throw an exception
        if (dollars != 0) {
            throw new BillsNotEnoughException("Not enough bills available to withdraw requested amount.");
        }
        return new ATMChange(twoCount, fiveCount, tenCount, fiftyCount, hundredCount);
    }
}
