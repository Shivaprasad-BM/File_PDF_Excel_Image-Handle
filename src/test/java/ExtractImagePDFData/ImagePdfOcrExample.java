package ExtractImagePDFData;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ImagePdfOcrExample {
	public static void main(String[] args) {
		String pdfFilePath = "C:\\Users\\user\\Downloads\\Screenshot 2024-07-25 200209.png"; // Path to the image-based
																								// PDF
		try {
			ITesseract tesseract = new Tesseract();
			// Perform OCR on the PDF
			String extractedText = tesseract.doOCR(new File(pdfFilePath));
			String[] words = extractedText.split("\\s+");
			String cleanedText = extractedText.replaceAll("\\s+", " ").trim();
			System.out.println(cleanedText);
			System.out.println(extractedText);
			System.out.println(words);
		} catch (TesseractException e) {
			e.printStackTrace();
		}

	}

}
