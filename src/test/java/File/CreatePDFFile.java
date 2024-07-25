package File;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.testng.annotations.Test;

import com.lowagie.text.pdf.PdfDocument;

public class CreatePDFFile {
	@Test
	void samplepdf() throws IOException {
		String folderName = "C:\\Users\\user\\Desktop\\";
		String pdfName = "kananans";
		String pdfPath = folderName + pdfName + ".pdf";
		File file = new File(pdfPath);
		if (!file.exists()) {
			PDDocument pdf = new PDDocument();
			PDPage page = new PDPage(PDRectangle.A4);
			pdf.addPage(page);
			pdf.save(file);
			pdf.close();
			System.out.println("createds");

		} else {
			System.out.println("alreay exist");
		}
	}

}
