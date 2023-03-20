package com.rjdxbanking.rjdxbank;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rjdxbanking.rjdxbank.Helpers.FirebaseInitialize;
import com.rjdxbanking.rjdxbank.Services.AccountService;




public class unitTesting {
    
    @BeforeClass
    public static void testBase(){
        FirebaseInitialize.initDatabase();
    }

    @Test
    public void testAccount(){
        Random rand = new Random();
        AccountService accService = new AccountService();
        String accountNumber = "";
        for(int i = 0; i < 10; i++){
            accountNumber = Integer.toString(100000000 + rand.nextInt(900000000));
            assertFalse(accService.checkAccountExist(accountNumber));
        }
        
    }

}
