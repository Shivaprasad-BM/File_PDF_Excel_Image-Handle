package ValidateExcel;

import java.util.*;
import java.util.stream.Collectors;

public class HeaderComparisonFinal {

	public static void main(String[] args) {
		// Example Inputs
		// Input 1
//	String result=	compareHeaders("1", "2", "[Level 1]", "[Level 1, Level 2]", "CIBC PRIVATE WEALTHWOOD GUNDY  heading    level 1",
//				"CIBC PRIVATE WEALTHWOOD GUNDY");

		String actualHeaderCount = "38";
		String expectedHeaderCount = "38";
		String actualHeaderOrder = "[level 1, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3]";
	  String expectedHeaderOrder = "[level 1, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 2, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3, level 3]";

		String actualHeaderNames = "column 3  Application for Creditor Insurance  for CIBC Personal Loans  heading    level 1\r\n"
				+ "Step 1 – Information about you and your CIBC Personal Loan  heading    level 2\r\n"
				+ "Step 2 – Insurance selection and average cost of insurance , per payment, for your personal loan  heading    level 2\r\n"
				+ "page 2  Step 3 – Please read carefully before signing  heading    level 2\r\n"
				+ "page 5  Introduction  heading    level 2\r\n"
				+ "Who can apply  heading    level 2\r\n"
				+ "page 6  When your Insurance begins  heading    level 2\r\n"
				+ "When your Insurance ends  heading    level 2\r\n"
				+ "Life Insurance  heading    level 2\r\n"
				+ "Description of the Life Insurance benefit  heading    level 3\r\n"
				+ "Special Provisions for Replacement Loans  heading    level 3\r\n"
				+ "What your Life Insurance costs  heading    level 3\r\n"
				+ "page 7  Disability Insurance  heading    level 2\r\n"
				+ "Definition of Disability/Disabled  heading    level 3\r\n"
				+ "Description of the Disability Insurance benefit  heading    level 3\r\n"
				+ "page 8  When your Disability Insurance benefits begin  heading    level 3\r\n"
				+ "When do your Disability Insurance benefit payments end  heading    level 3\r\n"
				+ "What your Disability Insurance costs  heading    level 3\r\n"
				+ "page 9  CIBC Payment Protector Insurance  heading    level 2\r\n"
				+ "Definition of Disability  heading    level 3\r\n"
				+ "Definition of Job Loss  heading    level 3\r\n"
				+ "Description of the Job Loss Insurance benefit  heading    level 3\r\n"
				+ "When your Job Loss Insurance benefits begin  heading    level 3\r\n"
				+ "When your Job Loss Insurance benefits end  heading    level 3\r\n"
				+ "What your CIBC Payment Protector Insurance costs  heading    level 3\r\n"
				+ "page 10  When an Insurance benefit will not be paid  heading    level 2\r\n"
				+ "page 11  Additional information regarding your Certificate of Insurance  heading    level 2\r\n"
				+ "How to cancel your insurance coverage  heading    level 3\r\n"
				+ "How do you make a claim  heading    level 3\r\n"
				+ "page 12  Who is the beneficiary of your insurance  heading    level 3\r\n"
				+ "Change of Insurer  heading    level 3\r\n"
				+ "Other things you should know about your Insurance  heading    level 3\r\n"
				+ "Canada Life Complaints Process  heading    level 3\r\n"
				+ "Provisions regarding legal action  heading    level 3\r\n"
				+ "Protecting your personal information  heading    level 3\r\n"
				+ "page 13  Information about CIBC  heading    level 3\r\n"
				+ "The Group Policy  heading    level 3\r\n"
				+ "How to contact Canada Life and CIBC Creditor Helpline  heading    level 3\r\n"
				+ "";

		String expectedHeaderNames = "Application for Creditor Insurance  for CIBC Personal Loans,\r\n"
				+ "Step 1 – Information about you and your CIBC Personal Loan,\r\n"
				+ "Step 2 – Insurance selection and average cost of insurance,per payment,for your personal loan\r\n"
				+ "Step 3 – Please read carefully before signing\r\n"
				+ "Introduction,\r\n"
				+ "Who can apply,\r\n"
				+ "When your Insurance begins,\r\n"
				+ "When your Insurance ends,\r\n"
				+ "Life Insurance,\r\n"
				+ "Description of the Life Insurance benefit,\r\n"
				+ "Special Provisions for Replacement Loans,\r\n"
				+ "What your Life Insurance costs,\r\n"
				+ "Disability Insurance,\r\n"
				+ "Definition of Disability/Disabled,\r\n"
				+ "Description of the Disability Insurance benefit,\r\n"
				+ "When your Disability Insurance benefits begin,\r\n"
				+ "When do your Disability Insurance benefit payments end,\r\n"
				+ "What your Disability Insurance costs,\r\n"
				+ "CIBC Payment Protector Insurance,\r\n"
				+ "Definition of Disability,\r\n"
				+ "Definition of Job Loss,\r\n"
				+ "Description of the Job Loss Insurance benefit,\r\n"
				+ "When your Job Loss Insurance benefits begin,\r\n"
				+ "When your Job Loss Insurance benefits end,\r\n"
				+ "What your CIBC Payment Protector Insurance costs,\r\n"
				+ "When an Insurance benefit will not be paid,\r\n"
				+ "Additional information regarding your Certificate of Insurance,\r\n"
				+ "How to cancel your insurance coverage,\r\n"
				+ "How do you make a claim,\r\n"
				+ "Who is the beneficiary of your insurance,\r\n"
				+ "Change of Insurer,\r\n"
				+ "Other things you should know about your Insurance,\r\n"
				+ "Canada Life Complaints Process,\r\n"
				+ "Provisions regarding legal action,\r\n"
				+ "Protecting your personal information,\r\n"
				+ "Information about CIBC,\r\n"
				+ "The Group Policy,\r\n"
				+ "How to contact Canada Life and CIBC Creditor Helpline";
		
		String result = compareHeaders(actualHeaderCount, expectedHeaderCount, actualHeaderOrder, expectedHeaderOrder,
				actualHeaderNames, expectedHeaderNames);

		System.out.println(result);
	}

	public static String compareHeaders(String actualCountStr, String expectedCountStr, String actualOrder,
			String expectedOrder, String actualNames, String expectedNames) {
		StringBuilder message = new StringBuilder();

		List<String> actualHeaders = normalizeInput(actualNames);
		List<String> expectedHeaders = normalizeInput(expectedNames);

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
		//	System.out.println("Acvtula headers "+actualHeader);
            String cleanedHeader = actualHeader.replaceAll("(?i)page \\d+", "").replaceAll("(?i)column \\d+", "").replaceAll("(?i)column+", "").replaceAll("^\\d+/\\d+", "").trim();
            String headerName = cleanedHeader.split("heading")[0].trim();
			
		//	System.out.println("heading name "+headerName);
		//	System.out.println("Expcted "+expectedHeaders);
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

}
