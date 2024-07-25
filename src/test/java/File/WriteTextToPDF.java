package File;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.testng.annotations.Test;

public class WriteTextToPDF {

	@Test
	void write() throws Throwable {
		String filePath = "C:\\Users\\user\\Desktop\\kananans19.pdf";
		File file = new File(filePath);
		PDDocument newpdf = new PDDocument();
		if (!file.exists()) {
			// Create pdf file

			PDPage newPage = new PDPage(); // PDPage(PDRectangle.A4);
			newpdf.addPage(newPage);
			newpdf.save(file);
			newpdf.close();
			System.out.println("Create new file");
		} else {
			System.out.println("Already exist");
			// Write date to pdf
			// -->To add new page in existing file
			PDPage newPage = new PDPage();// PDPage(PDRectangle.A4);
			newpdf.addPage(newPage);

			// PDPage newPage = newpdf.getPage(0);

			// to set text
			// PDPageContentStream content = new PDPageContentStream(newpdf, newPage);
			PDPageContentStream content = new PDPageContentStream(newpdf, newPage,
					PDPageContentStream.AppendMode.APPEND, true, true);

			// Add text to the page
			content.beginText();
			content.setFont(PDType1Font.HELVETICA_BOLD, 12);// Set font and font size
			content.setNonStrokingColor(0, 0, 0); // Black color// Set text color (you can set color using RGB values)
			content.newLineAtOffset(100, 700); // Position of the text
			content.showText("This is added text.");
			content.endText();

			content.close();
			newpdf.save(file);
			newpdf.close();
			System.out.println("success to write");
		}
	}

}
