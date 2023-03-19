package com.rjdxbanking.rjdxbank.Clients;

import com.rjdxbanking.rjdxbank.Entity.Account;

public class SessionClient {
    public static Account account;
    public static String cardNum;
    public static boolean ownBank;
    public static String navState;

    public static String getNavState() {
        return navState;
    }

    public static void setNavState(String navState) {
        SessionClient.navState = navState;
    }

    public static boolean isOwnBank() {
        return ownBank;
    }

    public static void setOwnBank(boolean ownBank) {
        SessionClient.ownBank = ownBank;
    }

    public static String getCardNum() {
        return cardNum;
    }

    public static void setCardNum(String cardNum) {
        SessionClient.cardNum = cardNum;
    }

    public static Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        SessionClient.account = account;
    }
}
