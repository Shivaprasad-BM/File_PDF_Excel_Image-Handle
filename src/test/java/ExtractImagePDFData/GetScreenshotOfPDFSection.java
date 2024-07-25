package ExtractImagePDFData;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;

public class GetScreenshotOfPDFSection {
	public static void main(String[] args) {
		Double width_cm = 6.7027;  
		Double height_cm = 2.7999;  
		Double startX_cm = 13.868;  
		Double startY_cm = 0.9041; 
		Integer PageNumber = 3;
		String FolderPath = "C:\\Users\\user\\Desktop\\testCrap";
		String PdfFilePath = "C:\\Users\\user\\Downloads\\2024-FC-EROLLGEN-S10-100-FinalRoll-Revision2-ENG-232-WI.pdf";
		String filePath = null;
		try {
			int dpi = 300;
			int startX = cmToPixels(startX_cm, dpi);
			int startY = cmToPixels(startY_cm, dpi);
			int width = cmToPixels(width_cm, dpi);
			int height = cmToPixels(height_cm, dpi);
			String fileName = "PdfSection" + new Random().nextInt(100) + ".PNG";
			filePath = Paths.get(FolderPath, new String[] { fileName }).toString();
			System.out.println(filePath);
			captureScreenshotFromPdf(PdfFilePath, PageNumber, startX, startY, width, height, filePath);

		} catch (Exception e) {

		}

	}

	public static void captureScreenshotFromPdf(String filePath, int pageNumber, int startX, int startY, int width,
			int height, String outputFileStoragePath) throws IOException {
		PDDocument document = PDDocument.load(new File(filePath));

		PDPage page = document.getPage(pageNumber - 1);

		Rectangle rect = new Rectangle(startX, startY, width, height);

		PDFRenderer renderer = new PDFRenderer(document);
		BufferedImage image = renderer.renderImageWithDPI(pageNumber - 1, 300); // 300 is the DPI (dots per inch)

		BufferedImage croppedImage = image.getSubimage(rect.x, rect.y, rect.width, rect.height);

		File output = new File(outputFileStoragePath);
		ImageIO.write(croppedImage, "png", output);

		document.close();
	}

	public static int cmToPixels(double cm, int dpi) {
		return (int) Math.round(cm * dpi / 2.54);
	}
}
