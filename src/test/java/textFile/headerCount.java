package textFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class headerCount {

	public static void main(String[] args) {
		
		String filePath = "C:\\CIBC\\extractedPDFText.txt"; // Update this with the correct file path
	
		List ouput = new ArrayList<>();
		
		
		// Use LinkedHashMap to maintain the insertion order
		Map<String, Integer> headingCounts = new LinkedHashMap<>();
		headingCounts.put("Level 1", 0);
		headingCounts.put("Level 2", 0);
		headingCounts.put("Level 3", 0);
		headingCounts.put("Level 4", 0);
		headingCounts.put("Level 5", 0);

		// Path to the text file containing the headings
		List<String> headingOrder = new ArrayList<>();
		Map<String, Integer> filteredCounts = new LinkedHashMap<>();
		try {
			// Read the file and count heading levels using regex
			// countHeadingLevels(filePath, headingCounts);
			countHeadingLevels(filePath, headingCounts, headingOrder);
		} catch (IOException e) {
			System.out.println("Error reading the file: " + e.getMessage());
		}

		// Calculate total headers
		int totalHeaders = headingCounts.values().stream().mapToInt(Integer::intValue).sum();
		headingCounts.put("Total Headers", totalHeaders);

	filteredCounts = filterHeadingCounts(headingCounts);

		// Output the report
//		printReport(filteredCounts);
//		printHeadingOrder(headingOrder);
		System.out.println(headingOrder);
		System.out.println(filteredCounts);
		ouput.add(filteredCounts);
		ouput.add(headingOrder);
		System.out.println(ouput);
		System.out.println(ouput.get(0));
		System.out.println(ouput.get(1));
		
	}

	// Method to count heading levels from the text file using regex
	// public static void countHeadingLevels(String filePath, Map<String, Integer>
	// headingCounts) throws IOException {
	public static void countHeadingLevels(String filePath, Map<String, Integer> headingCounts,
			List<String> headingOrder) throws IOException {

		// Regex to capture headings (e.g., "heading level 1", "heading level 2", etc.)
		Pattern pattern = Pattern.compile("heading\\s+level\\s+(\\d)");

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				// Match the pattern against the line
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					// Get the level from the regex group
					String level = matcher.group(1);

					String headingKey = "Level " + level;

					// Count the heading and add to the order list
					headingCounts.put(headingKey, headingCounts.get(headingKey) + 1);
					headingOrder.add(headingKey); // Always add to the list

					/*
					 * switch (level) { case "1": headingCounts.put("Level 1",
					 * headingCounts.get("Level 1") + 1); break; case "2":
					 * headingCounts.put("Level 2", headingCounts.get("Level 2") + 1); break; case
					 * "3": headingCounts.put("Level 3", headingCounts.get("Level 3") + 1); break; }
					 */
				}
			}
		}
	}

	public static Map<String, Integer> filterHeadingCounts(Map<String, Integer> headingCounts) {
		Map<String, Integer> filteredCounts = new LinkedHashMap<>();
		headingCounts.forEach((key, value) -> {
			if (value > 0) {
				filteredCounts.put(key, value);
			}
		});
		return filteredCounts;
	}

	// Method to print the report in key-value format
	public static void printReport(Map<String, Integer> headingCounts) {
		// Print each entry in the map
		headingCounts.forEach((key, value) -> System.out.println(key + " = " + value));
	}

	public static void printHeadingOrder(List<String> headingOrder) {
		System.out.println("Order of Headings: " + String.join(", ", headingOrder));
	}
	

}
