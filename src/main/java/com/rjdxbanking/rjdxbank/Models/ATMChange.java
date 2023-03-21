package com.rjdxbanking.rjdxbank.Models;

public class ATMChange {
    private int twoDollars;
    private int fiveDollars;
    private int tenDollars;
    private int fiftyDollars;
    private int hundredDollars;

    public ATMChange(int twoDollars, int fiveDollars, int tenDollars, int fiftyDollars, int hundredDollars) {
        this.twoDollars = twoDollars;
        this.fiveDollars = fiveDollars;
        this.tenDollars = tenDollars;
        this.fiftyDollars = fiftyDollars;
        this.hundredDollars = hundredDollars;
    }

    public int getTwoDollars() {
        return twoDollars;
    }

    public int getFiveDollars() {
        return fiveDollars;
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
