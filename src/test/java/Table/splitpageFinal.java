package Table;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class splitpageFinal {

	public static void main(String[] args) {
		String inputFilePath = "C:\\CIBC\\extractedpageTextFinal.txt";
		String outputFilePath = "C:\\Users\\user\\Desktop\\page6dataoutput.txt";
		StringBuilder pageSummary = new StringBuilder();

		String RangeofPage = "4";

		int Range[] = null;
		try {
			String content = readFile(inputFilePath);
			// Pattern tablePattern = Pattern.compile("(Page.*?)(out of region|$)",
			// Pattern.DOTALL);
			Pattern tablePattern = Pattern.compile("(Page.*?out of region)", Pattern.DOTALL);
	//		Pattern tablePattern = Pattern.compile("(Page \\d+.*?)(out of region|Page \\d+|$)", Pattern.DOTALL);


			Matcher tableMatcher = tablePattern.matcher(content);

			int pageCount = 1;

			// Loop through each found table
			while (tableMatcher.find()) {
				String pageData = tableMatcher.group(1); // Extract table data between "table" and "out of table"
				// tableSummary.append(tableData).append("\n");
				if (RangeofPage.contains(",")) {
					String[] rangeParts = RangeofPage.split(",");
					Range = new int[] { Integer.parseInt(rangeParts[0].trim()),
							Integer.parseInt(rangeParts[1].trim()) };

					if (pageCount >= Range[0] && pageCount <= Range[1]) {

						pageSummary.append(pageData).append("\n");
					}
				} else {
					// Handle the single page case
					int singlePage = Integer.parseInt(RangeofPage.trim());
					if (pageCount == singlePage) {
						pageSummary.append(pageData).append("\n"); // Append the data for the single page
					}
				}
				pageCount++;
			}
			System.out.println(pageSummary);
			writeFile(outputFilePath, pageSummary.toString());

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static String readFile(String filePath) throws IOException {
		StringBuilder contentBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				contentBuilder.append(currentLine).append("\n");
			}
		}
		return contentBuilder.toString();
	}

	public static void writeFile(String filePath, String content) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
			writer.write(content);
		}
	}
}
