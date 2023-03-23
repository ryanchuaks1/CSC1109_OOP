package com.rjdxbanking.rjdxbank;

import java.io.IOException;

import com.rjdxbanking.rjdxbank.Services.FXService;

public class Test {
    public static void main(String[] args) {
        FXService s = new FXService();
        try {
            System.out.println(s.fxpull());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
