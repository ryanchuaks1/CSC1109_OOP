package com.rjdxbanking.rjdxbank.Entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

public class ATMChange {

    @DocumentId
    private String Id;

    @PropertyName("twoDollars")
    private int twoDollars;

    @PropertyName("fiveDollars")
    private int fiveDollars;

    @PropertyName("tenDollars")
    private int tenDollars;

    @PropertyName("fiftyDollars")
    private int fiftyDollars;
    
    @PropertyName("hundredDollars")
    private int hundredDollars;

    //Required empty constructor for firestore
    public ATMChange()
    {}

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
