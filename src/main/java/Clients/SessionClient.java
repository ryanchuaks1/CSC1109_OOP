package Clients;

import Entity.Account;

public class SessionClient {
    public Account account;
    public String cardNum;
    public boolean ownBank;

    public boolean isOwnBank() {
        return ownBank;
    }

    public void setOwnBank(boolean ownBank) {
        this.ownBank = ownBank;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
}
