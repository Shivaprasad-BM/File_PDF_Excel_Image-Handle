package ValidateExcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HeaderComparison2 {

	public static void main(String[] args) {
		// Example Input
		String actualHeaderCount = "1";
		String expectedHeaderCount = "2";
		String actualHeaderOrder = "[Level 1]";
		String expectedHeaderOrder = "[Level 1, Level 2]";

		String actualHeaderNames = "CIBC PRIVATE WEALTHWOOD GUNDY    heading level 1";

		String expectedHeaderNames = "CIBC PRIVATE WEALTHWOOD GUNDY\n" + "Indemnity Agreement – Minors";

		// Compare headers and print the result
		String result = compareHeaders(actualHeaderCount, expectedHeaderCount, actualHeaderOrder, expectedHeaderOrder,
				actualHeaderNames, expectedHeaderNames);

		System.out.println(result);
		System.out.println("------------------");
	}


	public static String compareHeaders(String actualCountStr, String expectedCountStr, String actualOrder,
			String expectedOrder, String actualNames, String expectedNames) {
		StringBuilder message = new StringBuilder();

		// Normalize input for actual and expected header names
		List<String> actualHeaders = normalizeInput(actualNames);
		List<String> expectedHeaders = normalizeInput(expectedNames);
		List<String> expectedLevels = normalizeInput(expectedOrder.replaceAll("[\\[\\]]", ""));

		// Convert header counts from String to Integer
		int actualCount = Integer.parseInt(actualCountStr.trim());
		int expectedCount = Integer.parseInt(expectedCountStr.trim());

		// Compare header counts
		if (actualCount != expectedCount) {
			message.append("Mismatch in header count. Expected ").append(expectedCount).append(", but found ")
					.append(actualCount).append(".\n");
		}

		// Check for missing expected headers with their levels
		List<String> missingHeaders = new ArrayList<>();
		for (int i = 0; i < expectedHeaders.size(); i++) {
			String expectedHeader = expectedHeaders.get(i);
			String expectedLevel = expectedLevels.size() > i ? expectedLevels.get(i) : "unknown";
			boolean found = false;

			for (String actualHeader : actualHeaders) {
				// Extract the header name and level from the actual header
				String[] parts = actualHeader.split("heading", 2);
				String actualHeaderName = parts[0].trim();
				String actualHeaderLevel = parts.length > 1 ? parts[1].trim() : "";

				// Check for a match on the header name
				if (actualHeaderName.equalsIgnoreCase(expectedHeader)) {
					found = true;

					// Check if the level matches
					if (!actualHeaderLevel.equalsIgnoreCase(expectedLevel)) {
						message.append("Header \"").append(expectedHeader).append("\" is expected to be at  ")
								.append(expectedLevel).append(", but found at  ").append(actualHeaderLevel)
								.append(".\n");
					}
					break; // Exit the loop once we find a match
				}
			}

			if (!found) {
				missingHeaders.add("Header \"" + expectedHeader + "\" is expected to be at level " + expectedLevel);
			}
		}

		if (!missingHeaders.isEmpty()) {
			message.append("Missing headers: ").append(String.join("; ", missingHeaders)).append(". ")
					.append(missingHeaders.size()).append(" headers are missing.\n");
		}

		// Check for unexpected actual headers
		List<String> unexpectedHeaders = new ArrayList<>();
		for (String actualHeader : actualHeaders) {
			boolean isExpected = expectedHeaders.stream()
					.anyMatch(expectedHeader -> actualHeader.toLowerCase().startsWith(expectedHeader.toLowerCase()));
			if (!isExpected) {
				unexpectedHeaders.add(actualHeader);
			}
		}

		if (!unexpectedHeaders.isEmpty()) {
			message.append("Unexpected headers found: ").append(String.join(", ", unexpectedHeaders)).append(".\n");
		}

		// Compare expected and actual header levels
		List<String> actualOrderList = normalizeInput(actualOrder.replaceAll("[\\[\\]]", ""));
		if (!actualOrderList.equals(expectedLevels)) {
			message.append("Mismatch in header levels. Expected levels: ").append(expectedLevels)
					.append(", but found levels: ").append(actualOrderList).append(".\n");
		}

		// If no mismatches or missing headers
		if (message.length() == 0) {
			return "Headers and levels match as expected.";
		}

		return message.toString();
	}

	// Utility function to normalize input by handling varying whitespace
	private static List<String> normalizeInput1(String input) {
		return Arrays.stream(input.split("\n")).map(line -> line.trim().replaceAll("\\s+", " ")) // Replace multiple
																									// spaces with a
																									// single space
				.collect(Collectors.toList());
	}
	private static List<String> normalizeInput(String input) {
		return Arrays.stream(input.split("[,\n]")).map(String::trim).filter(s -> !s.isEmpty())
				.collect(Collectors.toList());
	}
}
