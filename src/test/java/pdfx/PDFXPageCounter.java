package pdfx;

import com.aspose.pdf.Document;

public class PDFXPageCounter {
	public static void main(String[] args) {
		Document pdfDocument = new Document("C:\\CIBC\\pdfs\\12782bil.pdfx.pdf");
		int pageCount = pdfDocument.getPages().size();
		System.out.println("Total Pages: " + pageCount);

	}

}
