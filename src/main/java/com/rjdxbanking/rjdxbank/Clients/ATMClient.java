package com.rjdxbanking.rjdxbank.Clients;

import com.rjdxbanking.rjdxbank.Entity.ATMChange;
import com.rjdxbanking.rjdxbank.Exception.BillsNotEnoughException;
import com.rjdxbanking.rjdxbank.Services.ATMService;

public class ATMClient {
    // The private values here determine how much cash count we have.
    private int tenDollars;
    private int fiftyDollars;
    private int hundredDollars;

    private ATMService aService = new ATMService();
    ATMChange achange = aService.getATMChange();

    public ATMClient() {
        tenDollars = achange.getTenDollars();
        fiftyDollars = achange.getFiftyDollars();
        hundredDollars = achange.getHundredDollars();
    }

    public ATMChange withdrawCash(int dollars) {
        int hundredCount = Math.min(hundredDollars, dollars / 100);
        dollars -= (hundredCount * 100);
        int fiftyCount = Math.min(fiftyDollars, dollars / 50);
        dollars -= (fiftyCount * 50);
        int tenCount = Math.min(tenDollars, dollars / 10);
        dollars -= (tenCount * 10);
        ATMChange remainder = new ATMChange(
                this.tenDollars - tenCount,
                this.fiftyDollars - fiftyCount, this.hundredDollars - hundredCount);
        ATMService aService = new ATMService();
        aService.updateATMChange(remainder);
        return new ATMChange(tenCount, fiftyCount, hundredCount);
    }

    public ATMChange checkChange(int dollars) throws BillsNotEnoughException {
        // Calculate the number of bills of each denomination needed to make up the
        // withdrawal amount
        int hundredCount = Math.min(hundredDollars, dollars / 100);
        dollars -= (hundredCount * 100);

        int fiftyCount = Math.min(fiftyDollars, dollars / 50);
        dollars -= (fiftyCount * 50);

        int tenCount = Math.min(tenDollars, dollars / 10);
        dollars -= (tenCount * 10);

        // If there is still an amount remaining, throw an exception
        if (dollars != 0) {
            throw new BillsNotEnoughException("Not enough bills available to withdraw requested amount.");
        }
        return new ATMChange(tenCount, fiftyCount, hundredCount);
    }

    public int getTenDollars() {
        return tenDollars;
    }

    public int getFiftyDollars() {
        return fiftyDollars;
    }

    public int getHundredDollars() {
        return hundredDollars;
    }

}
