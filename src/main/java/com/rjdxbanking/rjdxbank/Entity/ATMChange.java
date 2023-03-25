package com.rjdxbanking.rjdxbank.Entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

public class ATMChange {

    @DocumentId
    private String Id;

    @PropertyName("tenDollars")
    private int tenDollars;

    @PropertyName("fiftyDollars")
    private int fiftyDollars;

    @PropertyName("hundredDollars")
    private int hundredDollars;

    // Required empty constructor for firestore
    public ATMChange() {
    }

    public ATMChange(int tenDollars, int fiftyDollars, int hundredDollars) {
        this.tenDollars = tenDollars;
        this.fiftyDollars = fiftyDollars;
        this.hundredDollars = hundredDollars;
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
