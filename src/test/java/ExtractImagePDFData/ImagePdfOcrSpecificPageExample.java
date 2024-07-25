package ExtractImagePDFData;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImagePdfOcrSpecificPageExample {
	public static void main(String[] args) {
		String pdfFilePath = "C:\\Users\\user\\Downloads\\2024-FC-EROLLGEN-S10-100-FinalRoll-Revision2-ENG-232-WI.pdf";
		try {
			// Load the PDF document
			PDDocument document = PDDocument.load(new File(pdfFilePath));

			// Get the specific page you want to extract (e.g., first page)
			PDPageTree pages = document.getPages();
			PDPage page = pages.get(3); // Get the first page

			// Convert the page to an image
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			BufferedImage image = pdfRenderer.renderImageWithDPI(0, 300); // Adjust DPI as needed

			// Perform OCR on the image
			ITesseract tesseract = new Tesseract();
			// tesseract.setDatapath("path_to_tessdata"); // Set Tesseract data folder path
		
			try {
				String extractedText = tesseract.doOCR(image);
				System.out.println(extractedText);
			} catch (TesseractException e) {
				e.printStackTrace();
			}

			// Close the PDF document
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
