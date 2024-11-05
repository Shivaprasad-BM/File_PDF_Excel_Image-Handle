package ValidateExcel;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HeaderComparison1 {

    public static void main(String[] args) {
        // Example Input
        String actualHeaderCount = "1";
        String expectedHeaderCount = "2";
        String actualHeaderOrder = "[Level 1]";
        String expectedHeaderOrder = "[Level 1, Level 2]";

        String actualHeaderNames = "CIBC PRIVATE WEALTH WOOD GUNDY    heading    level 1";

        String expectedHeaderNames = "CIBC PRIVATE WEALTH WOOD GUNDY\n"
                + "Indemnity Agreement – Minors";

        // Compare headers and print the result
        String result = compareHeaders(actualHeaderCount, expectedHeaderCount, actualHeaderOrder, expectedHeaderOrder,
                actualHeaderNames, expectedHeaderNames);
        System.out.println(result);
    }

    public static String compareHeaders(String actualCountStr, String expectedCountStr, String actualOrder,
                                         String expectedOrder, String actualNames, String expectedNames) {
        StringBuilder message = new StringBuilder();

        // Normalize input for actual and expected header names
        List<String> actualHeaders = normalizeInput(actualNames);
        List<String> expectedHeaders = normalizeInput(expectedNames);

        // Convert header counts from String to Integer
        int actualCount = Integer.parseInt(actualCountStr.trim());
        int expectedCount = Integer.parseInt(expectedCountStr.trim());

        // Compare header counts
        if (actualCount != expectedCount) {
            message.append("Mismatch in header count. Expected ").append(expectedCount).append(", but found ")
                    .append(actualCount).append(".\n");
        }

        // Check for missing expected headers
        List<String> missingHeaders = new ArrayList<>();
        for (String expectedHeader : expectedHeaders) {
            if (!actualHeaders.stream().anyMatch(actualHeader -> actualHeader.startsWith(expectedHeader))) {
                missingHeaders.add(expectedHeader);
            }
        }

        if (!missingHeaders.isEmpty()) {
            message.append("Missing headers: ").append(String.join(", ", missingHeaders)).append(". ")
                    .append(missingHeaders.size()).append(" headers are missing.\n");
        }

        // Check for unexpected actual headers
        List<String> unexpectedHeaders = new ArrayList<>();
        for (String actualHeader : actualHeaders) {
            if (!expectedHeaders.stream().anyMatch(expectedHeader -> actualHeader.startsWith(expectedHeader))) {
                unexpectedHeaders.add(actualHeader);
            }
        }

        if (!unexpectedHeaders.isEmpty()) {
            message.append("Unexpected headers found: ").append(String.join(", ", unexpectedHeaders)).append(".\n");
        }

        // Compare expected and actual header levels
        List<String> actualOrderList = normalizeInput(actualOrder.replaceAll("[\\[\\]]", ""));
        List<String> expectedOrderList = normalizeInput(expectedOrder.replaceAll("[\\[\\]]", ""));

        if (!actualOrderList.equals(expectedOrderList)) {
            message.append("Mismatch in header levels. Expected levels: ").append(expectedOrderList)
                    .append(", but found levels: ").append(actualOrderList).append(".\n");

            // Check specific header level mismatches
            for (int i = 0; i < expectedHeaders.size(); i++) {
                if (i < actualHeaders.size()) {
                    String actualHeader = actualHeaders.get(i);
                    String expectedHeader = expectedHeaders.get(i);
                    String expectedLevel = expectedOrderList.size() > i ? expectedOrderList.get(i) : "unknown";
                    if (!actualHeader.toLowerCase().contains(expectedLevel.toLowerCase())) {
                        message.append("Header \"").append(expectedHeader).append("\" is expected to be at level ")
                                .append(expectedLevel).append(", but found in header: \"").append(actualHeader).append("\".\n");
                    }
                }
            }
        }

        // If no mismatches or missing headers
        if (message.length() == 0) {
            return "Headers and levels match as expected.";
        }

        return message.toString();
    }

    // Utility function to normalize input by handling varying whitespace
    private static List<String> normalizeInput(String input) {
        // Use regex to normalize spaces and trim
        return Arrays.stream(input.split("\n"))
                .map(line -> line.trim().replaceAll("\\s+", " ")) // Replace multiple spaces with a single space
                .collect(Collectors.toList());
    }
}
