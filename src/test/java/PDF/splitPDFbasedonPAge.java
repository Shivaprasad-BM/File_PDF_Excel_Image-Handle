package PDF;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class splitPDFbasedonPAge {

	public static void main(String[] args) {
		// Specify the path to your PDF file
		String pages = "5,7";
		File pdfFile = new File("C:\\CIBC\\6249A.pdf");

		// Specify the folder where you want to save the split PDFs
		String outputDir = "C:\\CIBC\\";
		List<String> extractedPaths = extractAndSavePages(pdfFile, pages, outputDir);
		System.out.println(extractedPaths);
		// Print the paths of the extracted PDF files
		// extractedPaths.forEach(System.out::println);
	}

	public static List<String> extractAndSavePages(File pdfFile, String pages, String outputDir) {

		List<String> savedPaths = new ArrayList<>();
		try (PDDocument document = PDDocument.load(pdfFile)) {
			int startPage = 1;
			int endPage = 0;
			if (pages.equalsIgnoreCase("all")) {
				endPage = document.getNumberOfPages();
			} else if (pages.contains(",")) {
				// Handle the range case (e.g., "2,5")
				String[] pageRange = pages.split(",");
				startPage = Integer.parseInt(pageRange[0].trim());
				endPage = Integer.parseInt(pageRange[1].trim());
			} else {
				// Handle the single page case (e.g., "4")
				startPage = endPage = Integer.parseInt(pages.trim());
			}

			// Get the total number of pages in the PDF

			// Split the PDF document, saving each page as a separate file

			for (int i = startPage - 1; i < endPage; i++) {
				int pageNumber = i + 1;
				if (pageNumber > 0 && pageNumber <= document.getNumberOfPages()) {
					// Create a new document for the current page
					PDDocument singlePageDoc = new PDDocument();

					// Extract the page at the current index
					PDPage page = document.getPage(i);

					// Add the extracted page to the new document
					singlePageDoc.addPage(page);

					// Save the new document as a separate PDF file in the specified output folder
					// singlePageDoc.save(outputDir + "output-page-" + (i + 1) + ".pdf");
					String outputFilePath = outputDir + "extracted-page-" + pageNumber + ".pdf";

					singlePageDoc.save(outputFilePath);

					// Close the newly created document
					singlePageDoc.close();
					savedPaths.add(outputFilePath);
					System.out.println("Page " + pageNumber + " extracted and saved to: " + outputFilePath);

				} else {
					System.out.println("Page " + pageNumber + " is out of bounds.");
				}

			}
			System.out.println("PDF split successfully. Files saved to: " + outputDir);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return savedPaths;
	}
}
