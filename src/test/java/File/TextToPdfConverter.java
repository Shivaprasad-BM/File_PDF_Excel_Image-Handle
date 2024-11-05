package File;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TextToPdfConverter {

	public static void main(String[] args) {
		String textFilePath = "C:\\Users\\user\\Desktop\\fr\\TranscribePDF_Golden.txt";
		String pdfFilePath = "C:\\Users\\user\\Desktop\\fr\\TranscribePDF_Golden.pdf";

		try {

			File pdfFile = new File(pdfFilePath);
			if (!pdfFile.getParentFile().exists()) {
				pdfFile.getParentFile().mkdirs(); // Create directories if they do not exist
			}

			// Create a PDFWriter instance
			PdfWriter writer = new PdfWriter(pdfFile);

			// Create a PDF document instance
			PdfDocument pdfDocument = new PdfDocument(writer);

			// Create a Document object to add text content
			Document document = new Document(pdfDocument);

			StringBuilder content = new StringBuilder();

			// Read the text file using UTF-8 charset
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(textFilePath), StandardCharsets.UTF_8));

			String line;

			// Loop through each line of the text file and add it to the PDF document

//			while ((line = reader.readLine()) != null) {
//				document.add(new Paragraph(line));
//			}

			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n"); // Append each line to the content
			}
			document.add(new Paragraph(content.toString()));

			// Close the document and reader
			document.close();
			reader.close();

			System.out.println("PDF created successfully.");
			System.out.println("Saving PDF to: " + pdfFile.getAbsolutePath());


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}