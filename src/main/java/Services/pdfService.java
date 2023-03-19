package Services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.itextpdf.html2pdf.HtmlConverter;

import Entity.Account;
import Models.TransactionType;

public class pdfService {

    public static void Receipt(Account account, TransactionType TransactionType, String amount) throws FileNotFoundException, IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();
        String substring = account.getId().substring(account.getId().length()-4,account.getId().length());

        String htmlString = "<img src='src/main/resources/images/IconPrimary.png'>"
            +"<p style='line-height:1.4'>Founded by Ryan, Jeff, Desmond Xavier</p>"
            + "<p>Transaction Receipt</p>"
            + "<hr>"
            + "<p style='line-height:1.0'>Account Id: "+"***********"+substring +"<p>"
            + "<p style='line-height:1.0'>Date: "+dtf.format(now) +"<p>"
            + "<p style='line-height:1.0'>Transaction type: "+TransactionType+"<br/>"
            + "<p style='line-height:1.0'>Amount: "+amount+ "<br/>";

        HtmlConverter.convertToPdf(htmlString, new FileOutputStream("src/main/resources/Receipt/"+dtf.format(now)+".pdf"));
        System.out.println("PDF Created!");
        
    }

}
