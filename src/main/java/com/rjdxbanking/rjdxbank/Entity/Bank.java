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

    public Bank() {

    }

    public Bank(String id, String bankName, String bankRoute, boolean isLocal, String currencyCode) {
        this.Id = id;
        this.bankName = bankName;
        this.bankRoute = bankRoute;
        this.isLocal = isLocal;
        this.currencyCode = currencyCode;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBankRoute(String bankRoute) {
        this.bankRoute = bankRoute;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

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

}
