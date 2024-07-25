package ExtractImagePDFData;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

public class ExtractImagePDGData {

	public static void main(String[] args) throws Throwable {
		String pdfContent = "";
		String FilePath = "C:\\Users\\user\\Downloads\\Screenshot 2024-07-25 200209.png";
		InputStream obj = new FileInputStream(new File(FilePath));
		PDDocument objDoc = PDDocument.load(obj);
		PDFTextStripper pdfStripper = new PDFTextStripper();
		pdfContent = pdfStripper.getText(objDoc);
		System.out.println(pdfContent);
		if (!pdfContent.contains(" ")) {
			System.out.println("if");
			ITesseract tesseract = new Tesseract();
			pdfContent = tesseract.doOCR(new File(FilePath));
			System.out.println("pdfdat" + pdfContent);

		}
	}
}
