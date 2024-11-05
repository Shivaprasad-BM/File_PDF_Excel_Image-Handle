package PDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;

public class PDFExtractRangeOrSinglePage {

    public static void main(String[] args) {
        // Example: String containing pages to extract, passed as an argument
        String pages = "1";
        
        // Specify the path to your PDF file
        File pdfFile = new File("C:\\Users\\user\\Downloads\\Headings.pdf");

        // Specify the folder where you want to save the extracted PDFs
        String outputDir = "C:\\CIBCnew\\";
        

        // Call the method to extract and save the specified pages
        String outputFilePath = extractAndSavePages(pdfFile, pages, outputDir);

        // Print the path of the extracted PDF file
        if (outputFilePath != null) {
            System.out.println("PDF extracted and saved to: " + outputFilePath);
        }
    }

    public static String extractAndSavePages(File pdfFile, String pages, String outputDir) {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            // Determine if it's a single page or a range
            int startPage;
            int endPage;

            if (pages.contains(",")) {
                // Handle the range case (e.g., "2,5")
                String[] pageRange = pages.split(",");
                startPage = Integer.parseInt(pageRange[0].trim());
                endPage = Integer.parseInt(pageRange[1].trim());
            } else {
                // Handle the single page case (e.g., "4")
                startPage = endPage = Integer.parseInt(pages.trim());
            }

            // Validate page numbers
            if (startPage <= 0 || endPage > document.getNumberOfPages() || startPage > endPage) {
                System.out.println("Invalid page range.");
                return null;
            }

            // Create a new document for the extracted pages
            PDDocument extractedDoc = new PDDocument();

            // Loop through the specified range and add pages to the new document
            for (int i = startPage - 1; i < endPage; i++) {
                PDPage page = document.getPage(i);
                extractedDoc.addPage(page);
            }

            // Define the output file path
            String outputFilePath;
            if (startPage == endPage) {
                outputFilePath = outputDir + "extracted-page-" + startPage + ".pdf";
            } else {
                outputFilePath = outputDir + "extracted-pages-" + startPage + "-to-" + endPage + ".pdf";
            }

            // Save the extracted pages to the output file
            extractedDoc.save(outputFilePath);

            // Close the extracted document
            extractedDoc.close();

            // Return the path of the saved PDF file
            return outputFilePath;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
