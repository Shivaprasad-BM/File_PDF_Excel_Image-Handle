package pdfx;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;

public class PDFTextExtractor {

	public static void main(String[] args) {
		File pdfFile = new File("C:\\CIBC\\pdfs\\12782bil.pdfx.pdf");

		try (PDDocument document = PDDocument.load(pdfFile)) {
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String text = pdfStripper.getText(document);
			System.out.println("Extracted text: \n" + text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
