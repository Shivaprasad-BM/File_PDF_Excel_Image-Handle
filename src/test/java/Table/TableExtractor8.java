package Table;
import java.io.*;
import java.util.regex.*;

public class TableExtractor8 {

    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\user\\Desktop\\tableIn.txt";
        String outputFilePath = "C:\\Users\\user\\Desktop\\tableOUT.txt";

        try {
            // Read the content of the input file
            String content = readFile(inputFilePath);

            // Create a pattern to match tables (from "table" to "out of table")
            Pattern tablePattern = Pattern.compile("table(.*?)out of table", Pattern.DOTALL);
            Matcher tableMatcher = tablePattern.matcher(content);

            StringBuilder output = new StringBuilder();
            int tableCount = 1;

            // Loop through each found table
            while (tableMatcher.find()) {
                String tableData = tableMatcher.group(1);  // Extract table data between "table" and "out of table"
                output.append("Table ").append(tableCount).append(":\n");

                // Process rows in the table
                String[] rows = tableData.split("row");
                int rowCount = 1;

                for (String row : rows) {
                    if (row.trim().isEmpty()) continue;  // Skip empty rows

                    // Process columns in each row
                    String[] columns = row.split("column");
                    int columnCount = 1;

                    // Initialize row data
                    boolean rowDataCaptured = false;

                    for (String column : columns) {
                        // Ignore any empty or redundant column numbers
                        String columnData = column.trim();
                        if (!columnData.isEmpty() && !columnData.matches("\\d+")) {
                            if (!rowDataCaptured) {
                                output.append("  Row ").append(rowCount).append(":\n");
                                rowDataCaptured = true;
                            }
                            output.append("    Column ").append(columnCount).append(": ").append(columnData).append("\n");
                            columnCount++;
                        }
                    }

                    if (rowDataCaptured) {
                        rowCount++;
                    }
                }

                output.append("\n");
                tableCount++;
            }

            // Write the extracted table data to the output file
            writeFile(outputFilePath, output.toString());

            System.out.println("Table data has been successfully extracted!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to read file content into a string
    public static String readFile(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
        }
        return contentBuilder.toString();
    }

    // Helper method to write string content into a file
    public static void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
}
