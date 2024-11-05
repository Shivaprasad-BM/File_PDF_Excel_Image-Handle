package Table;

import java.io.*;
import java.util.regex.*;

public class TableDataExtractorNew2 {

	public static void main(String[] args) {
		String inputFilePath = "C:\\Users\\user\\Desktop\\tableIn.txt";
		String outputFilePath = "C:\\Users\\user\\Desktop\\tableOUTnew.txt";

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
				String tableData = tableMatcher.group(1); // Extract table data between "table" and "out of table"

				// Pattern to match rows
				Pattern rowPattern = Pattern.compile("row\\s*(\\d+)(.*?)(?=(row|out of table|$))", Pattern.DOTALL);
				Matcher rowMatcher = rowPattern.matcher(tableData);

				int rowCount = 0;
				int maxColumnCount = 0;

				// First pass to count rows and columns
				while (rowMatcher.find()) {
					rowCount++; // Count each row

					// Pattern to match columns within the row
					String rowData = rowMatcher.group(2).trim();
					Pattern colPattern = Pattern.compile("column\\s*(\\d+)\\s*(.*?)(?=(column|row|out of table|$))",
							Pattern.DOTALL);
					Matcher colMatcher = colPattern.matcher(rowData);

					int columnCount = 0;
					while (colMatcher.find()) {
						columnCount++; // Count each column
					}

					// Track the maximum number of columns in any row
					if (columnCount > maxColumnCount) {
						maxColumnCount = columnCount;
					}
				}

				// Output table summary with row and column counts
				output.append("Table ").append(tableCount).append(" with ").append(rowCount).append(" rows and ")
						.append(maxColumnCount).append(" columns:\n");

				// Reset the row matcher to process rows again
				rowMatcher.reset();
				int currentRowCount = 1;
				String lastColumnHeader = ""; // To store last column when it's not explicitly mentioned
				String currentColumnData = ""; // To handle multi-line row data

				// Process each row again to extract and display the actual data
				while (rowMatcher.find()) {
					String rowData = rowMatcher.group(2).trim();
					if (rowData.isEmpty())
						continue; // Skip empty rows

					output.append("  Row ").append(currentRowCount).append(":\n");

					// Pattern to match columns within the row
					Pattern colPattern = Pattern.compile("column\\s*(\\d+)\\s*(.*?)(?=(column|row|out of table|$))",
							Pattern.DOTALL);
					Matcher colMatcher = colPattern.matcher(rowData);

					int columnCount = 1;
					boolean isColumnFound = false;

					while (colMatcher.find()) {
						String columnData = colMatcher.group(2).trim();
						if (!columnData.isEmpty()) {
							// Output the current column data
							output.append("    Column ").append(columnCount).append(": ").append(columnData)
									.append("\n");
							lastColumnHeader = "Column " + columnCount; // Save the last column
							columnCount++;
							isColumnFound = true;
						}
					}

					// If no new columns are found but data exists, append to the last known column
					// header
					if (!isColumnFound && !rowData.isEmpty()) {
						output.append("    ").append(lastColumnHeader).append(": ").append(rowData).append("\n");
					}

					currentRowCount++;
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
