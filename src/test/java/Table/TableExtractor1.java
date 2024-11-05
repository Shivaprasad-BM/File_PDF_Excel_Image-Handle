package Table;

import java.io.*;
import java.util.*;

public class TableExtractor1 {

	public static void main(String[] args) {
		String inputFilePath = "C:\\Users\\user\\Desktop\\tableIn.txt";
		String outputFilePath = "C:\\Users\\user\\Desktop\\tableOUT.txt";

		try {
			// Read the content of the input file
			String content = readFile(inputFilePath);

			// Split by "table" and "out of table"
			String[] tables = content.split("table");
			System.out.println(tables[1]);
			StringBuilder output = new StringBuilder();

			int tableCount = 1;

			// Loop through each table block
			for (String tableData : tables) {
				if (tableData.contains("out of")) {
					output.append("Table ").append(tableCount).append(":\n");
					System.out.println(output.toString());
					// Extract rows and columns from the table
					String[] rows = tableData.split("row");
					int rowCount = 1;

					for (String row : rows) {
						if (row.contains("column")) {
							output.append("  Row ").append(rowCount).append(":\n");
							System.out.println("sssss"+output.toString());
							String[] columns = row.split("column");

							int columnCount = 1;
							for (String column : columns) {
								if (!column.trim().isEmpty()) {
									output.append("    Column ").append(columnCount).append(": ").append(column.trim())
											.append("\n");
									System.out.println(output.toString());
									columnCount++;
								}
							}
							rowCount++;
						}
					}

					tableCount++;
					output.append("\n");
				}
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
