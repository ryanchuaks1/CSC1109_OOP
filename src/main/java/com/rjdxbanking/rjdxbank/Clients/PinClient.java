package com.rjdxbanking.rjdxbank.Clients;

import com.rjdxbanking.rjdxbank.Entity.Account;

public class PinClient {

    public static boolean checkPin(String pin) {
        if (SessionClient.isOwnBank()) {
            AccountClient accountClient = new AccountClient();
            String cardNum = SessionClient.getCardNum().substring(6, 15);
            try {
                Account account = accountClient.Login(cardNum, pin);
                SessionClient.setAccount(account);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return true;
        }
    }

}
