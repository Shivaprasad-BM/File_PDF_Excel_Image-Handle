package tableHeader;

import java.io.*;
import java.sql.Driver;
import java.util.regex.*;

public class TableHeaderHandle {

	public static void main(String[] args) {
		String inputFilePath = "C:\\CIBC\\extractedPDFText.txt";
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
//					if (!rowData.startsWith("column")) {
					if (!rowData.contains("column 1")) {
						rowData = "column 1  " + rowData.trim();
						// System.out.println(rowData);
					}
					// System.out.println(rowData);
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
//						System.out.println("countt--------     "+rowData);
//						rowCount--;
//					}
//					if (rowData.matches(".*\\btable\\b.*")) {
//						System.out.println("countt--------     "+rowData);
//					    rowCount--;
//					}
					if (rowData.contains("table")) {
						String[] words = rowData.split("\\s+"); // Split the row data into words

						for (String word : words) {
							if (word.equals("table")) {
								rowCount--;
								break; // Exit the loop after finding "table"
							}
						}
					}

				}

				// Output table summary with row and column counts
				output.append("Table ").append(tableCount).append(" with ").append(rowCount).append(" rows and ")
						.append(maxColumnCount).append(" columns\n");

				// variable output this

				tableSummary.append("Table ").append(tableCount).append(" with ").append(rowCount).append(" rows and ")
						.append(maxColumnCount).append(" columns\n");

				// Reset the row matcher to process rows again
				rowMatcher.reset();
				int currentRowCount = 1;

				// Process each row again to extract and display the actual data
				while (rowMatcher.find()) {
					String rowData = rowMatcher.group(2).trim();
					if (rowData.isEmpty())
						continue; // Skip empty rows

//					if (rowData.contains("table")) {
//						output.append("  Row ").append(currentRowCount).append("\n");
//						currentRowCount--;
//					}
//					if (rowData.matches(".*\\btable\\b.*")) {
//						System.out.println("countt--------     "+rowData);
//					    rowCount--;
//					}
					boolean isTableStandalone = false;
					if (rowData.contains("table")) {
						String[] words = rowData.split("\\s+"); // Split the row data into words

						for (String word : words) {
							if (word.equals("table")) {
								output.append("  Row ").append(currentRowCount).append("\n");
								currentRowCount--;
								  isTableStandalone = true;
								break; // Exit the loop after finding "table"
							}
						}
						if (!isTableStandalone) {
					        output.append("  Row ").append(currentRowCount).append("\n");
					    }
					}

					else {
						output.append("  Row ").append(currentRowCount).append("\n");
					}

					// output.append(" Row ").append(currentRowCount).append("\n");
					System.out.println(rowData);
					// Add "Column 1" for rows not starting with "column"
//					if (!rowData.startsWith("column")) {
					if (!rowData.contains("column 1")) {
						rowData = "column 1  " + rowData.trim();
						// System.out.println(rowData);
					}
					if (currentRowCount > 1) {
						System.out.println(rowData);
						if (rowData.contains("Maximum Investment in Voting Shares Property")) {
							System.out.println("yes");
							rowData = rowData.replaceAll("Maximum Investment in Voting Shares Property", "");

						}
						if (rowData.contains("Placements maximums dans des Actions avec droit de vote")) {
							rowData = rowData.replaceAll("Placements maximums dans des Actions avec droit de vote", "");
						}
					}
					// Pattern to match columns within the row
					Pattern colPattern = Pattern.compile("column\\s*(\\d+)\\s*(.*?)(?=(column|row|out of table|$))",
							Pattern.DOTALL);
					// Pattern colPattern =
					// Pattern.compile("column\\s*(\\d+)\\s*(.*?)(?=(column\\s*\\d+|row|out of
					// table|$))", Pattern.DOTALL);
					// Pattern colPattern =
					// Pattern.compile("column\\s*(\\d+)\\s*(.*?)(?=(\\n|column\\s*\\d+|row|out of
					// table|$))", Pattern.DOTALL);
					// Pattern colPattern =
					// Pattern.compile("column\\s*(\\d+)\\s*(.*?)(?=(\\s+column\\s*\\d+|\\s*row|\\s*out
					// of table|$))", Pattern.DOTALL);
					// Pattern colPattern =
					// Pattern.compile("column\\s*(\\d+)\\s*(.+?)(?=(\\s+column\\s*\\d+|\\s*row|\\s*out
					// of table|$))", Pattern.DOTALL);

					Matcher colMatcher = colPattern.matcher(rowData);

					int columnCount = 1; // Start column counting from 1
					while (colMatcher.find()) {
						// String columnData = colMatcher.group(1);
						String columnData = colMatcher.group(2).trim().split("\n")[0];
//						if (currentRowCount > 1) {
//							if (columnData.contains("Maximum Investment in Voting Shares Property")) {
//								System.out.println("yes");
//								columnData=columnData.replaceAll("Maximum Investment in Voting Shares Property", "");
//							}
//						}
						System.out.println("column data                      " + columnData);
						if (!columnData.isEmpty()) {
							// Output the current column data
							// System.out.println("hi");
							output.append("    Column ").append(columnCount).append(" ").append(columnData)
									.append("\n");
							columnCount++;
						} else {
							output.append("    Column ").append(columnCount).append(" ").append("NULL").append("\n");
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
