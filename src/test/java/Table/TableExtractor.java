package Table;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class TableExtractor {

	public static void main(String[] args) {
		// Input and output file paths
		String inputFilePath = "C:\\Users\\user\\Desktop\\tableIn.txt";
		String outputFilePath = "C:\\Users\\user\\Desktop\\tableOUT.txt";

		// Read the input file
		String fileContent = readFile(inputFilePath);
//		System.out.println(fileContent);
		// Split the content into individual tables
		List<List<String[]>> tables = extractTables(fileContent);

		// Write the extracted table data to the output file
		writeTablesToFile(tables, outputFilePath);

		System.out.println("Table data extraction completed. Check " + outputFilePath);
	}

	// Reads the content of the input file
	public static String readFile(String filePath) {
		StringBuilder content = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				content.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	// Splits the content into separate tables, then splits each table into rows and
	// columns
	public static List<List<String[]>> extractTables(String text) {
		List<List<String[]>> tables = new ArrayList<>();

		// Pattern to identify each table and its rows/columns
		Pattern tablePattern = Pattern.compile("table([^table]+)"); // Splits by "table" keyword
		Matcher tableMatcher = tablePattern.matcher(text);

		while (tableMatcher.find()) {

			System.out.println(tableMatcher);
			String tableData = tableMatcher.group(1).trim(); // Extract table content
			List<String[]> rows = new ArrayList<>(); // List to store rows of the table

			// Split table content by rows (e.g., "row 1", "row 2", etc.)
			Pattern rowPattern = Pattern.compile("row \\d+([^row]+)");
			Matcher rowMatcher = rowPattern.matcher(tableData);

			while (rowMatcher.find()) {
				String row = rowMatcher.group(1).trim();
				String[] columns = new String[10]; // Assuming max 10 columns per row

				// Regex to extract column data within each row
				Pattern columnPattern = Pattern.compile("column \\d+\\s+([^column]+)");
				Matcher columnMatcher = columnPattern.matcher(row);

				while (columnMatcher.find()) {
					String colValue = columnMatcher.group(1).trim();

					// Get column number and map the value to the correct index
					Pattern colNumPattern = Pattern.compile("column (\\d+)");
					Matcher colNumMatcher = colNumPattern.matcher(columnMatcher.group(0));

					if (colNumMatcher.find()) {
						int colIndex = Integer.parseInt(colNumMatcher.group(1)) - 1;
						columns[colIndex] = colValue;
					}
				}
				rows.add(columns); // Add the row (with columns) to the table
			}
			tables.add(rows); // Add the full table to the list of tables
		}

		return tables;
	}

	// Writes the tables to the output file
	public static void writeTablesToFile(List<List<String[]>> tables, String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (int t = 0; t < tables.size(); t++) {
				writer.write("Table " + (t + 1) + ":");
				writer.newLine();

				List<String[]> rows = tables.get(t);
				for (int i = 0; i < rows.size(); i++) {
					writer.write("  Row " + (i + 1) + ":");
					writer.newLine();

					String[] columns = rows.get(i);
					for (int j = 0; j < columns.length; j++) {
						if (columns[j] != null) {
							writer.write("    Column " + (j + 1) + ": " + columns[j]);
							writer.newLine();
						}
					}
					writer.newLine();
				}
				writer.newLine(); // Separate each table with a blank line
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
