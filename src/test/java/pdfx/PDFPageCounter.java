package pdfx;

import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;
import java.io.IOException;

public class PDFPageCounter {

	public static void main(String[] args) {
		// Specify the path to your .pdfx.pdf file
		File pdfFile = new File("C:\\CIBC\\pdfs\\12782bil.pdfx.pdf");
		// Load the PDF and get the page count
		try (PDDocument document = PDDocument.load(pdfFile)) {
			int pageCount = document.getNumberOfPages();
			System.out.println("Total number of pages: " + pageCount);
		} catch (IOException e) {
			System.err.println("An error occurred while loading the PDF.");
			e.printStackTrace();
		}
	}
}
