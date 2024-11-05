package end2end;

import java.util.ArrayList;
import java.util.List;

public class RangeToListConverter {

	public static void main(String[] args) {
		String input = "2,5-9"; // Example input with only one number, no commas or ranges
		List<Integer> resultList = generateListFromInput(input);

		System.out.println("Generated List: " + resultList);
	}

	public static List<Integer> generateListFromInput(String input) {
		List<Integer> resultList = new ArrayList<>();

		// Step 1: Split the input by commas (or directly use it if there are no commas)
		String[] elements = input.split(",\\s*");

		for (String element : elements) {
			element=element.replaceAll("[a-zA-Z]", "");
			// Step 2: Check if the element contains a hyphen (indicating a range)
			if (element.contains("-")) {
				// Split by hyphen to get the start and end of the range
				String[] rangeParts = element.split("-");
				int start = Integer.parseInt(rangeParts[0].trim());
				int end = Integer.parseInt(rangeParts[1].trim());

				// Add all numbers from start to end (inclusive) to the result list
				for (int i = start; i <= end; i++) {
					resultList.add(i);
				}
			} else {
				// If it's a single number, add it directly to the list
				resultList.add(Integer.parseInt(element.trim()));
			}
		}

		return resultList;
	}
}
