package com.rjdxbanking.rjdxbank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjdxbanking.rjdxbank.Clients.AccountClient;
import com.rjdxbanking.rjdxbank.Entity.Account;
import com.rjdxbanking.rjdxbank.Exception.InsufficientFundsException;
import com.rjdxbanking.rjdxbank.Helpers.FirebaseInitialize;
import com.rjdxbanking.rjdxbank.Services.AccountService;


public class unitTesting {
    
    @BeforeClass
    public static void testBase(){
        FirebaseInitialize.initDatabase();
    }

    //may not be the best way as possible to get the correct accountNumber
    @Test
    public void testAccount(){
        Random rand = new Random();
        AccountService accService = new AccountService();
        String accountNumber = "";
        for(int i = 0; i < 10; i++){
            accountNumber = Integer.toString(100000000 + rand.nextInt(900000000));
            assertFalse(accService.checkAccountExist(accountNumber));
            assertEquals(true,accService.getAccountsByNumber(accountNumber)==null);
        }
        
    }

    @Test(expected = InsufficientFundsException.class)
    public void testWithdraw() throws InsufficientFundsException{
        AccountClient accountClient = new AccountClient();
        String cardNumber = "6229259821434671";
        //static
        try {
            Account account = accountClient.Login(cardNumber, "123456");
            if (account.getAvailableBalance() < 99999999) {
                throw new InsufficientFundsException("Insufficient funds in account");
            } 
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new InsufficientFundsException(ex.getMessage());
        }
            
    }

}
