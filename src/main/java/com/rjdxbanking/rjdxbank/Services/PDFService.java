package com.rjdxbanking.rjdxbank.Services;

import com.itextpdf.html2pdf.HtmlConverter;
import com.rjdxbanking.rjdxbank.Clients.SessionClient;
import com.rjdxbanking.rjdxbank.Entity.Account;
import com.rjdxbanking.rjdxbank.Entity.Bank;
import com.rjdxbanking.rjdxbank.Models.TransactionType;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PDFService {
    // normal receipt for same bank
    public static void Receipt(Account account, TransactionType TransactionType, String amount)
            throws FileNotFoundException, IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd_MM_yyyy HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String substring = account.getId().substring(account.getId().length() - 4, account.getId().length());

        String htmlString = "<img src='src/main/resources/com/rjdxbanking/rjdxbank/Images/IconPrimary.png'>"
                + "<p style='line-height:1.4'>Founded by Ryan, Jeff, Desmond Xavier</p>"
                + "<p>Transaction Receipt</p>"
                + "<hr>"
                + "<p style='line-height:1.0'>Account Id: " + "***********" + substring + "<p>"
                + "<p style='line-height:1.0'>Date: " + dtf.format(now) + "<p>"
                + "<p style='line-height:1.0'>Transaction type: " + TransactionType + "<br/>"
                + "<p style='line-height:1.0'>Amount: " + amount + "<br/>"
                + "<p style='line-height:1.0'>Remaining Balance: " + account.getBalance().getAvailableBalance()
                + "<br/>";

        try {
            HtmlConverter.convertToPdf(htmlString, new FileOutputStream(
                    "src/main/resources/com/rjdxbanking/rjdxbank/Receipt/receipt_"
                            + dtf2.format(now) + ".pdf"));
            System.out.println("PDF Created!");
        } catch (FileNotFoundException e) {
            System.out.println("Print Error");
        }
    }

    // Other overseas banks for otherBankWithdrawalController
    public static void otherReceipt(Bank bank, TransactionType TransactionType, String SGDamount,
            String ConvertedAmount, String fxRate)
            throws FileNotFoundException, IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd_MM_yyyy HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();

        String htmlString = "<img src='src/main/resources/com/rjdxbanking/rjdxbank/Images/IconPrimary.png'>"
                + "<p style='line-height:1.4'>Founded by Ryan, Jeff, Desmond Xavier</p>"
                + "<p>Transaction Receipt</p>"
                + "<hr>"
                + "<p style='line-height:1.0'>Bank name: " + bank.getBankName() + "</p>"
                + "<p style='line-height:1.0'>Currency code: " + SessionClient.currency + "</p>"
                + "<p style='line-height:1.0'>Date: " + dtf.format(now) + "</p>"
                + "<p style='line-height:1.0'>Transaction type: " + TransactionType + "</p>"
                + "<p style='line-height:1.0'>Foreign exchange rate: 1 SGD = " + fxRate + " " + SessionClient.currency
                + "</p>"
                + "<p style='line-height:1.0'>Amount($SGD): " + SGDamount + "</p>"
                + "<p style='line-height:1.0'>Amount($" + SessionClient.currency + "): "
                + ConvertedAmount + "</p>";

        try {
            HtmlConverter.convertToPdf(htmlString, new FileOutputStream(
                    "src/main/resources/com/rjdxbanking/rjdxbank/Receipt/receipt_"
                            + dtf2.format(now) + ".pdf"));
            System.out.println("PDF Created!");
        } catch (FileNotFoundException e) {

            System.out.println("Print Error");
        }
    }

    // for localbanks for otherBankWithdrawalController
    public static void otherReceipt(Bank bank, TransactionType TransactionType, String SGDamount)
            throws FileNotFoundException, IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd_MM_yyyy HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();

        String htmlString = "<img src='src/main/resources/com/rjdxbanking/rjdxbank/Images/IconPrimary.png'>"
                + "<p style='line-height:1.4'>Founded by Ryan, Jeff, Desmond Xavier</p>"
                + "<p>Transaction Receipt</p>"
                + "<hr>"
                + "<p style='line-height:1.0'>Bank name: " + bank.getBankName() + "</p>"
                + "<p style='line-height:1.0'>Currency code: " + SessionClient.currency + "</p>"
                + "<p style='line-height:1.0'>Date: " + dtf.format(now) + "</p>"
                + "<p style='line-height:1.0'>Transaction type: " + TransactionType + "</p>"
                + "<p style='line-height:1.0'>Amount($SGD): " + SGDamount + "</p>";

        try {
            HtmlConverter.convertToPdf(htmlString, new FileOutputStream(
                    "src/main/resources/com/rjdxbanking/rjdxbank/Receipt/receipt_"
                            + dtf2.format(now) + ".pdf"));
            System.out.println("PDF Created!");
        } catch (FileNotFoundException e) {
            System.out.println("Print Error");
        }
    }
}
