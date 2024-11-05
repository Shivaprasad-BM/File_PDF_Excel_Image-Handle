package fields;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class testing {

	public static void main(String[] args) {
		// Example input strings
		String filePath = "C:\\Users\\user\\Desktop\\formFields.txt";
//		String expectedOutput = "Name<TF>\n" + "Account Number<TF>\n" + "FC Name /Number<TF>\n" + "name of<TF>\n"
//				+ "I,<TF>\n" + "Date (Month day, year)<TF>\n" + "Dated this<DD>\n" + "day of<DD>\n" + "year<TF>\n"
//				+ "Date (Month day, year)<TF>\n" + "Signature of Parent / Legal Guardian (sign within box)<TF>";

		String expectedOutput = "Name<TF>\r\n"
				+ "Account Number<TF>\r\n"
				+ "FC Name /Number<TF>\r\n"
				+ "name of<TF>\r\n"
				+ "I,<TF>\r\n"
				+ "Date (Month day, year)<TF>\r\n"
				+ "Day<DD>\r\n"
				+ "Month<DD>\r\n"
				+ "year<TF>\r\n"
				+ "Date (Month day, year)<TF> \r\n"
				+ "Signature of Parent / Legal Guardian (sign within box)<TF>";
		
		// Convert expected output string to a list
//		List<String> expectedList = Arrays.asList(expectedOutput.split("\\n"));
		 List<String> expectedList = Arrays.stream(expectedOutput.split("\\n"))
                 .map(String::trim)  // Trim each line
                 .collect(Collectors.toList());

		   System.out.println("Expected List: " + expectedList);

		List<String> result = processFile(filePath);

		// Compare the processed list with the expected list
		compareLists(result, expectedList);
	}

	// Method to process the file and return a list of processed lines
	private static List<String> processFile(String filePath) {
		List<String> result = new ArrayList<>(); // List to store processed lines

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			// Regular expression to match both "edit **** blank" and "edit blank"
			String pattern = "edit\\s+.*?\\s+blank";
			Pattern regex = Pattern.compile(pattern);

			while ((line = reader.readLine()) != null) {
				Matcher matcher = regex.matcher(line);

				// Case 1: If the line contains "radio button not checked", replace it with
				// "<RB>"
				if (line.contains("radio button  not checked")) {
					line = line.replace("radio button  not checked", "<RB>");
				}
				// Case 2: If the line contains "combo box", replace it with "<DD>"
				else if (line.contains("combo box  1/1")) {
					line = line.replace("combo box  1/1", "<DD>");
				}
				// Case 3: If the line contains "edit **** blank" or "edit blank", replace with
				// "<TF>"
				else if (matcher.find()) {
					line = line.replace(matcher.group(0), "<TF>");
				}

				// Remove any spaces between the field text and tags (like "/Number <TF>"
				// becomes "/Number<TF>")
				line = line.replaceAll("\\s+<", "<"); // Removes spaces before the placeholder tags

				// Trim the line
				line = line.trim();

				// Add only lines containing '<' to the result list
				if (line.contains("<")) { // Only add lines that contain '<'
					result.add(line); // Add to result list
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	// Method to compare the processed lines with the expected list
	private static String compareLists(List<String> result, List<String> expectedList) {

		StringBuilder MissingData = new StringBuilder();
		StringBuilder OrderMismatch = new StringBuilder();

		for (int i = 0; i < Math.min(result.size(), expectedList.size()); i++) {
			if (!result.contains(expectedList.get(i))) {
				if (result.get(i).equals(expectedList.get(i))) {
				} else {
//					dataMismatch.add("Expected: " + expectedList.get(i) + ", Found: " + result.get(i));
					MissingData.append(("Expected: " + expectedList.get(i) + ", Found: " + result.get(i))).append("\n");
				}
			}
		}

		// Check for order mismatch and matching data
		for (int i = 0; i < Math.min(result.size(), expectedList.size()); i++) {
			if (result.contains(expectedList.get(i))) {
				if (result.get(i).equals(expectedList.get(i))) {
				} else {
//					orderMismatch.add("Expected: " + expectedList.get(i) + ", Found: " + result.get(i));
					OrderMismatch.append(("Expected: " + expectedList.get(i) + ", Found: " + result.get(i)))
							.append("\n");
				}
			}
		}

		StringBuilder builder = new StringBuilder();
		builder.append("Missing Data-->\n").append(MissingData).append("\n\n").append("Order Mismatch-->\n")
				.append(OrderMismatch);

		System.out.println(builder.toString());
		
		
		return builder.toString();
		}
}