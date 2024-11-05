package Table;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class splitPageContent {

    public static void main(String[] args) {
        String inputFilePath = "C:\\CIBC\\extractedPDFText.txt";  // Input text file
        String outputFilePath = "C:\\Users\\user\\Desktop\\page6dataoutput.txt";  // Output text file
        String pageRange = "1";  // Example range: page 1 to 2

        try {
            // Read the input file
            String content = readFile(inputFilePath);

            // Split content based on "Page X of Y" format
            List<String> pages = splitContentByPage(content);

            // Extract content for the specified range
            String extractedContent = extractContentForRange(pages, pageRange);

            // Write the extracted content to the output file
            writeFile(outputFilePath, extractedContent);

            System.out.println("Content extracted and written to file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to read the file content
    public static String readFile(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }
        return contentBuilder.toString();
    }

    // Method to split content by "Page X of Y" and keep content between pages
    public static List<String> splitContentByPage(String content) {
        List<String> pages = new ArrayList<>();
        Pattern pagePattern = Pattern.compile("Page \\d+ of \\d+", Pattern.DOTALL);
        Matcher matcher = pagePattern.matcher(content);

        int lastIndex = 0;
        while (matcher.find()) {
            if (lastIndex != 0) {
                pages.add(content.substring(lastIndex, matcher.start()).trim());
            }
            lastIndex = matcher.start();
        }
        if (lastIndex != 0) {
            pages.add(content.substring(lastIndex).trim());
        }

        return pages;
    }

    // Method to extract content based on a page range (e.g., "1-2")
    public static String extractContentForRange(List<String> pages, String pageRange) {
        StringBuilder extractedContent = new StringBuilder();

        int[] range = parsePageRange(pageRange);
        int startPage = range[0];
        int endPage = range[1];

        for (int i = startPage - 1; i < endPage && i < pages.size(); i++) {
            extractedContent.append(pages.get(i)).append("\n");
        }

        return extractedContent.toString();
    }

    // Helper method to parse the page range (e.g., "1-2")
    public static int[] parsePageRange(String pageRange) {
        String[] rangeParts = pageRange.split("-");
        int startPage = Integer.parseInt(rangeParts[0].trim());
        int endPage = rangeParts.length > 1 ? Integer.parseInt(rangeParts[1].trim()) : startPage;

        return new int[]{startPage, endPage};
    }

    // Method to write content to a file
    public static void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
}
