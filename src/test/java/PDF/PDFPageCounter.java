package PDF;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

public class PDFPageCounter {
	public static void main(String[] args) {
		// Specify the path to your PDF file
		String pdfFilePath = "C:\\CIBC\\6249A.pdf";

		// Load the PDF document
		try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
			// Get the number of pages
			int pageCount = document.getNumberOfPages();
			System.out.println("Number of pages: " + pageCount);
			int rangeValue = getRangeValue(pageCount);
			System.out.println("Range value: " + rangeValue);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static int getRangeValue(int pageCount) {
		if (pageCount <= 10) {
			return 1;
		} else {
			return (int) Math.ceil((pageCount - 10) / 5.0) + 1;
		}
	}
}