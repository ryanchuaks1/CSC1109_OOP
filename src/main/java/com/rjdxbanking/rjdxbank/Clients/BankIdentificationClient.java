package com.rjdxbanking.rjdxbank.Clients;

import com.rjdxbanking.rjdxbank.Entity.Bank;
import com.rjdxbanking.rjdxbank.Services.BankService;

public class BankIdentificationClient {

    public boolean isValidBank(String BIN) {
        BankService bankService = new BankService();
        Bank bank = bankService.getBankByRoute(BIN);
        if (bank != null) {
            if (BIN.equals("622925")) {
                SessionClient.setOwnBank(true);
            } else {
                String currency = bank.getCurrencyCode();
                SessionClient.setCurrency(currency);
                SessionClient.setOwnBank(false);
            }
            return true;
        } else {
            return false;
        }
    }
}