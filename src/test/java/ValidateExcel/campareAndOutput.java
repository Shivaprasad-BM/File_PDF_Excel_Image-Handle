package ValidateExcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class campareAndOutput {

	public static String compareHeaders(String actualCount, String expectedCount, String actualOrder,
			String expectedOrder, String actualNames, String expectedNames) {
		StringBuilder comments = new StringBuilder();

		int expectedHeaderCount = Integer.parseInt(expectedCount);
		int actualHeaderCount = Integer.parseInt(actualCount);
		int missingCount = expectedHeaderCount - actualHeaderCount;

// Compare header counts
		if (missingCount > 0) {
			comments.append("The expected header count is ").append(expectedCount).append(", but only ")
					.append(actualCount).append(" header(s) were found. ").append(missingCount)
					.append(" header(s) are missing. ");
		}

// Compare header orders
		if (!actualOrder.equals(expectedOrder)) {
			comments.append("The expected header levels are [").append(expectedOrder)
					.append("], but the actual header levels are [").append(actualOrder).append("]. ");
		}

// Compare header names
		List<String> actualHeaderList = Arrays.asList(actualNames.split("\n"));
		List<String> expectedHeaderList = Arrays.asList(expectedNames.split("\n"));
		List<String> missingLevels = new ArrayList<>();

		for (int i = 0; i < expectedHeaderList.size(); i++) {
			String expectedName = expectedHeaderList.get(i);
			if (i < actualHeaderList.size()) {
				String actualName = actualHeaderList.get(i);
				if (!actualName.equals(expectedName)) {
					comments.append("Header name mismatch at index ").append(i + 1).append(": expected \"")
							.append(expectedName).append("\" but found \"").append(actualName).append("\". ");
				}
			} else {
				comments.append("Missing header at index ").append(i + 1).append(": \"").append(expectedName)
						.append("\" (expected level: ").append(getExpectedLevel(expectedOrder, i)).append("). ");
				missingLevels.add(getExpectedLevel(expectedOrder, i));
			}
		}

// Add missing levels information
		if (!missingLevels.isEmpty()) {
			comments.append("Missing header levels are: ").append(String.join(", ", missingLevels)).append(". ");
		}

		if (comments.length() == 0) {
			return "No discrepancies found.";
		} else {
			return comments.toString();
		}
	}

//Helper method to determine the expected header level
	private static String getExpectedLevel(String expectedOrder, int index) {
		String[] levels = expectedOrder.replaceAll("[\\[\\]]", "").split(", ");
		return index < levels.length ? levels[index].trim() : "unknown";
	}

	public static void main(String[] args) {

		String actualHeaderCount = "1";
		String expectedHeaderCount = "2";
		String actualHeaderOrder = "[Level 1]";
		String expectedHeaderOrder = "Level 1, Level 2";
		String actualHeaderNames = "CIBC PRIVATE WEALTHWOOD GUNDY";
		String expectedHeaderNames = "CIBC PRIVATE WEALTH WOOD GUNDY ,Indemnity Agreement – Minors";

		String result = compareHeaders(actualHeaderCount, expectedHeaderCount, actualHeaderOrder, expectedHeaderOrder,
				actualHeaderNames, expectedHeaderNames);
		System.out.println(result);

	}
}
