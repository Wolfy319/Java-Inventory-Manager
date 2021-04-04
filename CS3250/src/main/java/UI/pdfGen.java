package UI;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.io.*;
public class pdfGen {
    
public void reportGen() throws FileNotFoundException{
    FileOutputStream destination = new FileOutputStream("CS3250\\src\\main\\java\\UI\\Reports");
    PdfWriter writer = new PdfWriter(destination);
    PdfDocument salesReportObj = new PdfDocument(writer);
    salesReportObj.addNewPage(); 
    Document salesReport = new Document(salesReportObj); 
}



}
