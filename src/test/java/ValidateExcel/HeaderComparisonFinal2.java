package ValidateExcel;

import java.util.*;
import java.util.stream.Collectors;

public class HeaderComparisonFinal2 {

	public static void main(String[] args) {
		// Example Inputs
		// Input 1
//	String result=	compareHeaders("1", "2", "[Level 1]", "[Level 1, Level 2]", "CIBC PRIVATE WEALTHWOOD GUNDY  heading    level 1",
//				"CIBC PRIVATE WEALTHWOOD GUNDY");

		String actualHeaderCount = "4";
		String expectedHeaderCount = "3";
		String actualHeaderOrder = "[Level 1, Level 2, Level 2, Level 3]";
		String expectedHeaderOrder = "[Level 2, Level 2, Level 3]";

		String actualHeaderNames = "Agreement for Lost, Destroyed or Stolen Instruments  heading    level 1\r\n"
				+ "Affidavit Information  heading    level 2\r\n" + "1. Client Information  heading    level 2\r\n"
				+ "Home Address  heading    level 3";

		String expectedHeaderNames = "Affidavit Information\r\n" + "1. Client Information\r\n" + "Home Address";

		String result = compareHeaders(actualHeaderCount, expectedHeaderCount, actualHeaderOrder, expectedHeaderOrder,
				actualHeaderNames, expectedHeaderNames);

		System.out.println(result);
	}

	public static String compareHeaders(String actualCountStr, String expectedCountStr, String actualOrder,
			String expectedOrder, String actualNames, String expectedNames) {
		StringBuilder message = new StringBuilder();

		List<String> actualHeaders = normalizeInput1(actualNames);
		List<String> expectedHeaders = normalizeInput1(expectedNames);

		int actualCount = Integer.parseInt(actualCountStr.trim());
		int expectedCount = Integer.parseInt(expectedCountStr.trim());

		// Compare header counts
		if (actualCount != expectedCount) {
			message.append("Mismatch in header count. Expected ").append(expectedCount).append(", but found ")
					.append(actualCount).append(".\n");
		}

		// Check for missing expected headers
		for (String expectedHeader : expectedHeaders) {
			if (!actualHeaders.stream().anyMatch(header -> header.contains(expectedHeader))) {
				message.append("Missing header: \"").append(expectedHeader).append("\" is expected to be at  ")
						.append(getExpectedLevel(expectedHeader, expectedOrder)).append(".\n");
			}
		}

		// Check for unexpected actual headers
		for (String actualHeader : actualHeaders) {
			// System.out.println("Acvtula headers "+actualHeader);
			String cleanedHeader = actualHeader.replaceAll("(?i)page \\d+", "").replaceAll("(?i)column \\d+", "")
					.replaceAll("(?i)column+", "").replaceAll("^\\d+/\\d+", "").trim();
			String headerName = cleanedHeader.split("heading")[0].trim();

			// System.out.println("heading name "+headerName);
			// System.out.println("Expcted "+expectedHeaders);
			if (!expectedHeaders.contains(headerName)) {
				message.append("Unexpected header found: \"").append(headerName).append("\".\n");
			}
		}

		// Compare expected and actual header levels if order information is provided
		List<String> actualOrderList = normalizeInput(actualOrder.replaceAll("[\\[\\]]", ""));
		List<String> expectedOrderList = normalizeInput(expectedOrder.replaceAll("[\\[\\]]", ""));

		for (int i = 0; i < Math.min(actualOrderList.size(), expectedOrderList.size()); i++) {
			if (!actualOrderList.get(i).equalsIgnoreCase(expectedOrderList.get(i))) {
				message.append("Header \"").append(expectedHeaders.get(i)).append("\" is expected to be at ")
						.append(expectedOrderList.get(i)).append(", but found at  ").append(actualOrderList.get(i))
						.append(".\n");
			}
		}

		if (message.length() == 0) {
			// System.out.println("Headers and levels match as expected.");
			return "Headers and levels match as expected.";
		} else {
			// System.out.println(message.toString());
			return message.toString();
		}

	}

	private static String getExpectedLevel(String header, String expectedOrder) {
		List<String> expectedLevels = normalizeInput(expectedOrder.replaceAll("[\\[\\]]", ""));
		// Logic to determine expected level based on header name or index
		// For simplicity, return the first level (or implement more complex logic if
		// needed)
		return expectedLevels.size() > 1 ? expectedLevels.get(1) : "unknown"; // Adjust index based on your needs
	}

	private static List<String> normalizeInput(String input) {
		return Arrays.stream(input.split("[,\n]")).map(String::trim).filter(s -> !s.isEmpty())
				.collect(Collectors.toList());
	}

	private static List<String> normalizeInput1(String input) {
		return Arrays.stream(input.split("[\n]")).map(String::trim).filter(s -> !s.isEmpty())
				.collect(Collectors.toList());
	}

}
