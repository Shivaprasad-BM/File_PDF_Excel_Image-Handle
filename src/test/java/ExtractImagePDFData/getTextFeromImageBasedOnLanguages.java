package ExtractImagePDFData;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class getTextFeromImageBasedOnLanguages {
	private static ITesseract tesseract;

	public static void main(String[] args) throws TesseractException {

		tesseract = new Tesseract();
		tesseract.setDatapath("C:\\tessdata");
		tesseract.setLanguage("eng");
		String imagePath = "C:\\Temp\\PdfSection19.PNG";

		// Perform OCR on the image
		String text = tesseract.doOCR(new File(imagePath));

		// Print the extracted text (optional)
		System.out.println("Extracted Text: \n" + text);

	}

}