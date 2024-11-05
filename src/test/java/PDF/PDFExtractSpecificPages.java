
package PDF;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFExtractSpecificPages {

	public static void main(String[] args) {
		// Example: String containing pages to extract, passed as an argument
		String pages = "4,7";

		// Specify the path to your PDF file
		File pdfFile = new File("C:\\CIBC\\6249A.pdf");

		// Specify the folder where you want to save the extracted PDFs
		String outputDir = "C:\\CIBC\\";

		// Call the method to extract and save specific pages
		List<String> extractedPaths = extractAndSavePages(pdfFile, pages, outputDir);
		System.out.println(extractedPaths);
		// Print the paths of the extracted PDF files
		extractedPaths.forEach(System.out::println);
	}

	public static List<String> extractAndSavePages(File pdfFile, String pages, String outputDir) {
		List<String> savedPaths = new ArrayList<>();

		try (PDDocument document = PDDocument.load(pdfFile)) {
			// Split the pages string by comma to get individual page numbers
			String[] pagesArray = pages.split(",");

			// Loop through the page numbers to extract and save each
			for (String pageStr : pagesArray) {
				int pageNumber;
				try {
					pageNumber = Integer.parseInt(pageStr.trim());
				} catch (NumberFormatException e) {
					System.out.println("Invalid page number: " + pageStr);
					continue;
				}

				// Ensure the page number is within the valid range
				if (pageNumber > 0 && pageNumber <= document.getNumberOfPages()) {
					// Create a new document for the specific page
					PDDocument singlePageDoc = new PDDocument();

					// Extract the page (0-based indexing internally)
					PDPage page = document.getPage(pageNumber - 1);

					// Add the extracted page to the new document
					singlePageDoc.addPage(page);

					// Define the output file path
					String outputFilePath = outputDir + "extracted-page-" + pageNumber + ".pdf";

					// Save the new document as a separate PDF file
					singlePageDoc.save(outputFilePath);

					// Close the newly created document
					singlePageDoc.close();

					// Add the saved file path to the list
					savedPaths.add(outputFilePath);

					System.out.println("Page " + pageNumber + " extracted and saved to: " + outputFilePath);
				} else {
					System.out.println("Page " + pageNumber + " is out of bounds.");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return savedPaths;
	}
}
