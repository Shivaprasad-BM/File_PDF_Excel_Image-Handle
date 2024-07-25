package File;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.annotations.Test;

public class GetDatFromPDF {
	@Test
	void get() throws Throwable {
		String filepath = "C:\\\\Users\\\\user\\\\Desktop\\\\kananans19.pdf";
		File file = new File(filepath);
		PDDocument pdf = PDDocument.load(file);
	//	PDPage page = pdf.getPage(0); // specified page to get text
		PDFTextStripper text = new PDFTextStripper();
	//	 text.setStartPage(0);
	//	 text.setEndPage(1); //or custmize range
		String getedtext = text.getText(pdf);
		System.out.println(getedtext);
		pdf.close();
	}
}
