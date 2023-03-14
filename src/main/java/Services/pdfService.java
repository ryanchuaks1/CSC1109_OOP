package Services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.itextpdf.html2pdf.HtmlConverter;

import Models.TransactionType;

public class pdfService {

    public static void depositReceipt(TransactionType TransactionType, String amount) throws FileNotFoundException, IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();

        String htmlString = "<img src='src/main/resources/images/IconPrimary.png'>"
            +"<p style='line-height:1.4'>Founded by Ryan, Jeff, Desmond Xavier</p>"
            + "<p>Transaction Receipt</p>"
            + "<hr>"
            + "<p style='line-height:1.0'>Date: "+dtf.format(now) +"<p>"
            + "<p style='line-height:1.0'>Transaction type: "+TransactionType+"<br/>"
            + "<p style='line-height:1.0'>Amount: "+amount+ "<br/>";

        HtmlConverter.convertToPdf(htmlString, new FileOutputStream("src/main/resources/Receipt/test.pdf"));
        System.out.println("PDF Created!");
        // // created PDF document instance
        // Document doc = new Document();
        // try {
        // // generate a PDF at the specified location
        // PdfWriter writer = PdfWriter.getInstance(doc,
        // new FileOutputStream("resourcesMotivation.pdf"));
        // System.out.println("PDF created.");
        // // opens the PDF
        // doc.open();

        // // adds paragraph to the PDF file
        // doc.add(new Paragraph(
        // "<h1>If you're offered a seat on a rocket ship, don't ask what seat! Just get
        // on.</h1>"));

        // // close the PDF file
        // doc.close();
        // // closes the writer
        // writer.close();
        // } catch (DocumentException e) {
        // e.printStackTrace();
        // } catch (FileNotFoundException e) {
        // e.printStackTrace();
        // }
    }

}
