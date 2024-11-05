package pdfx;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import java.io.IOException;

public class PDFPageCounter1 {

    public static void main(String[] args) {
        try {
            // Path to your .pdfx.pdf file
            PdfDocument pdfDoc = new PdfDocument(new PdfReader("C:\\CIBC\\pdfs\\12782bil.pdfx.pdf"));
            // Get number of pages
            int pageCount = pdfDoc.getNumberOfPages();
            System.out.println("Number of pages: " + pageCount);
            pdfDoc.close();
        } catch (IOException e) {
            System.err.println("Failed to read the PDF file.");
            e.printStackTrace();
        }
    }
}
