package com.rjdxbanking.rjdxbank.Entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

public class Bank {
    @DocumentId
    private String Id;

    @PropertyName("bankName")
    private String bankName;

    @PropertyName("bankRoute")
    private String bankRoute;

    @PropertyName("isLocal")
    private boolean isLocal;
    
    @PropertyName("currencyCode")
    private String currencyCode;


    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getId() {
        return Id;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankRoute() {
        return bankRoute;
    }

    public boolean isLocal() {
        return isLocal;
    }


}
