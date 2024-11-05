package textFile;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeadingValidatorFromFile3 {
	public static void main(String[] args) {
		// Paths to the input and output files
		String inputFilePath = "C:\\CIBC\\extractedPDFText.txt";
		String outputFilePath = "C:\\Users\\user\\Desktop\\heading level testout3.txt";

		List<String> processedLines = new ArrayList<>();
		Stack<Integer> levelStack = new Stack<>();
		boolean firstHeading = true; // Flag to handle the first heading

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
			String line;

			// Read all lines from the input file
			while ((line = reader.readLine()) != null) {
				int currentLevel = extractHeadingLevel(line);
				System.out.println(line);

				if (currentLevel > 0) {
					if (firstHeading) {
						processedLines.add(line);
						levelStack.push(currentLevel);
						firstHeading = false;
					} else {
						String errorMessage = validateHeadingOrder(levelStack, currentLevel);
						if (!errorMessage.isEmpty()) {
							processedLines.add(line + " " + errorMessage);
						} else {
							processedLines.add(line);
						}
						levelStack.push(currentLevel);
					}
				} else {
					// If not a heading line, keep it unchanged
					processedLines.add(line);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Write back the processed lines to the output file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
			for (String line : processedLines) {
				writer.write(line);
				writer.newLine(); // Add a new line after each line
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("File processed successfully. Check the output file for results.");
	}

	// Method to extract the heading level from the line
	public static int extractHeadingLevel(String line) {
		if (line.contains("heading") && line.contains("level")) {
			try {
				// Extract the number after "heading level" (e.g., "heading level 2" -> 2)
				// String levelString = line.substring(line.indexOf("heading level") + 14,
				// line.indexOf("heading level") + 15);
				Pattern pattern = Pattern.compile("heading\\s+level\\s+(\\d+)");
				Matcher matcher = pattern.matcher(line);
				String levelString = "";
				if (matcher.find()) {
					// Extract the level number
					levelString = matcher.group(1);
				//	System.out.println(levelString);
				} else {
					System.out.println("No heading level found in the line.");
				}
				return Integer.parseInt(levelString);
			} catch (Exception e) {
				// Handle any parsing errors
				return 0;
			}
		}
		return 0; // Return 0 if it's not a heading line
	}

	// Method to validate heading order
	public static String validateHeadingOrder(Stack<Integer> levelStack, int currentLevel) {
		StringBuilder errors = new StringBuilder();

		if (levelStack.isEmpty()) {
			if (currentLevel != 1) {
				errors.append("[ERROR: Heading order error - No previous heading]");
			}
			return errors.toString();
		}

		if (currentLevel == 1) {
			if (!levelStack.isEmpty()) {
				errors.append("[ERROR: Level 1 heading should be the first heading; No previous headings expected]");
			}
		} else {
			while (!levelStack.isEmpty() && levelStack.peek() >= currentLevel) {
				levelStack.pop();
			}
 
			if (levelStack.isEmpty() && currentLevel != 1) {
				errors.append("[ERROR: Heading order error - Missing Level 1 heading]");
			} else if (!levelStack.isEmpty() && levelStack.peek() != currentLevel - 1) {
				if (currentLevel == 2 && levelStack.peek() != 1) {
					errors.append("[ERROR: Level 2 heading should follow a Level 1 heading; Level 1 is missing]");
				} else if (currentLevel == 3 && levelStack.peek() != 2) {
					errors.append("[ERROR: Level 3 heading should follow a Level 2 heading; Level 2 is missing]");
				} else if (currentLevel == 4 && levelStack.peek() != 3) {
					errors.append("[ERROR: Level 4 heading should follow a Level 3 heading; Level 3 is missing]");
				} else {
					errors.append("[ERROR: Invalid heading order]");
				}
			}
		}

		return errors.toString();
	}
}
