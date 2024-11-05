package textFile;

import java.io.*;
import java.util.*;

public class HeadingValidatorFromFile {
	public static void main(String[] args) {
		// Path to the input file
		String inputFilePath = "C:\\Users\\user\\Desktop\\headinglevel.txt"; // Change this to the path of your
																					// file
		String outputFilePath = "C:\\Users\\user\\Desktop\\heading level tesoutt.txt";
		
		List<String> lines = new ArrayList<>();
		List<String> errors = new ArrayList<>();
		Stack<Integer> levelStack = new Stack<>();
		boolean firstHeading = true; // Flag to handle the first heading

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
			String line;

			// Read all lines from the input file
			while ((line = reader.readLine()) != null) {
				// Process each line and validate if it contains a heading
				int currentLevel = extractHeadingLevel(line);

				if (currentLevel > 0) {
					if (firstHeading) {
						errors.add(line);
						levelStack.push(currentLevel);
						firstHeading = false;
					} else {
						// Validate heading order
						while (!levelStack.isEmpty() && levelStack.peek() > currentLevel) {
							levelStack.pop();
						}
						if (levelStack.isEmpty() || levelStack.peek() != currentLevel - 1) {
							errors.add(line + " [ERROR: Invalid heading order]");
						} else {
							errors.add(line);
						}
						levelStack.push(currentLevel);
					}
				} else {
					// If not a heading line, keep it unchanged
					errors.add(line);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Write back the processed lines to the output file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
			for (String line : errors) {
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
		if (line.contains("heading level")) {
			try {
				// Extract the number after "heading level" (e.g., "heading level 2" -> 2)
				String levelString = line.substring(line.indexOf("heading level") + 14,
						line.indexOf("heading level") + 15);
				return Integer.parseInt(levelString);
			} catch (Exception e) {
				// Handle any parsing errors
				return 0;
			}
		}
		return 0; // Return 0 if it's not a heading line
	}
}
