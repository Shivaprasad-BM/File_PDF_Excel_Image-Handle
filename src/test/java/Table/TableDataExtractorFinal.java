package Table;

import java.io.*;
import java.util.regex.*;

public class TableDataExtractorFinal {

	public static void main(String[] args) {
		String inputFilePath = "C:\\Users\\user\\Desktop\\tableIn.txt";
		String outputFilePath = "C:\\Users\\user\\Desktop\\tableOUTnew.txt";
		StringBuilder tableSummary = new StringBuilder();

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
				// System.out.println(tableCount);
				// System.out.println(tableData);
				// Pattern to match rows
				Pattern rowPattern = Pattern.compile("row\\s*(\\d+)(.*?)(?=(row|out of table|$))", Pattern.DOTALL);
				Matcher rowMatcher = rowPattern.matcher(tableData);

				int rowCount = 0;
				int maxColumnCount = 0;

				// Initialize the summary variable

				// First pass to count rows and columns
				while (rowMatcher.find()) {
					rowCount++; // Count each row

					// Pattern to match columns within the row
					String rowData = rowMatcher.group(2).trim();
					// System.out.println(rowData);

					if (!rowData.startsWith("column")) {
						// output.append(" Column 1: ").append(rowData).append("\n");
						rowData = "column 1  " + rowData.trim();
						// System.out.println(rowData);
					}
					System.out.println(rowData);
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
//					if (rowData.contains("table")) {
//						rowCount--;
//					}
					if (rowData.matches(".*\\btable\\b.*")) {
					    rowCount--;
					}
				}
			//	System.out.println(maxColumnCount);

				// Output table summary with row and column counts
				
				output.append("Table ").append(tableCount).append(" with ").append(rowCount).append(" rows and ")
						.append(maxColumnCount).append(" columns\n");
			//	System.out.println(output);
				// variable output this

				tableSummary.append("Table ").append(tableCount).append(" with ").append(rowCount).append(" rows and ")
						.append(maxColumnCount).append(" columns\n");

				// Reset the row matcher to process rows again
				rowMatcher.reset();
				int currentRowCount = 1;

				// Process each row again to extract and display the actual data
				while (rowMatcher.find()) {
					String rowData = rowMatcher.group(2).trim();

					// System.out.println(rowData);
					if (rowData.isEmpty())
						continue; // Skip empty rows

					
					if (rowData.contains("table")) {
						output.append("  Row ").append(currentRowCount).append("\n");
						currentRowCount--;
					}
					else {
					output.append("  Row ").append(currentRowCount).append("\n");
					}
					// Add "Column 1" for rows not starting with "column"
					if (!rowData.startsWith("column")) {
						rowData = "column 1  " + rowData.trim();
						// System.out.println(rowData);
					}
					// Pattern to match columns within the row
					Pattern colPattern = Pattern.compile("column\\s*(\\d+)\\s*(.*?)(?=(column|row|out of table|$))",
							Pattern.DOTALL);
					Matcher colMatcher = colPattern.matcher(rowData);

					int columnCount = 1; // Start column counting from 1
					while (colMatcher.find()) {
						String columnData = colMatcher.group(2).trim();
						if (!columnData.isEmpty()) {
							// Output the current column data
							output.append("    Column ").append(columnCount).append(" ").append(columnData)
									.append("\n");
							columnCount++;
						}

					}
					// System.out.println("row count "+currentRowCount+"column count "+columnCount);

					currentRowCount++;
				}

				output.append("\n");
				tableCount++;
			}
			tableSummary.append("total table count" + (tableCount - 1));
			writeFile(outputFilePath, output.toString());
			//

			// System.out.println(tableSummary.toString());
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
