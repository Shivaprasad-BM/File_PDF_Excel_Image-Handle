package Table;

import java.io.*;
import java.util.regex.*;

public class splitTable {

	public static void main(String[] args) {
		String inputFilePath = "C:\\CIBC\\extractedpageTextFinal.txt";
		String outputFilePath = "C:\\Users\\user\\Desktop\\tablesplit.txt";
		StringBuilder tableSummary = new StringBuilder();
		try {
			// Read the content of the input file
			String content = readFile(inputFilePath);

			// Create a pattern to match tables (from "table" to "out of table")
			//Pattern tablePattern = Pattern.compile("table(.*?)out of table", Pattern.DOTALL);
		//	Pattern tablePattern = Pattern.compile("\\btable\\b([\\s\\S]*?)\\bout of table\\b", Pattern.DOTALL);
			Pattern tablePattern = Pattern.compile("\\btable\\b.*?(row\\s+\\d+\\s+column\\s+\\d+.*?)(?=\\bout of table\\b)", Pattern.DOTALL);


			Matcher tableMatcher = tablePattern.matcher(content);

			int tableCount = 1;

			// Loop through each found table
			while (tableMatcher.find()) {
				String tableData = tableMatcher.group(1); // Extract table data between "table" and "out of table"
				tableSummary.append(tableCount + ".\n").append("table ").append(tableData).append("\n");
				tableCount++;
			}
			System.out.println(tableSummary);
			writeFile(outputFilePath, tableSummary.toString());

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

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

	public static void writeFile(String filePath, String content) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write(content);
		}
	}
}
