package end2end;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class RemoveSequentialDuplicates {

	public static void main(String[] args) {
		String filePath = "C:\\CIBC\\extractedPDFText.txt"; // Update with your file path

		try {
			// Process the file and write the output back
			processFile(filePath);
			System.out.println("Processed lines written to: " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to process the file
	private static void processFile(String filePath) throws IOException {
		// Read all lines from the file
//		List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);

	

		// StringBuilder to hold the final output
		StringBuilder outputLines = new StringBuilder();

		// Set to track unique lines for the first four lines
		Set<String> firstFourUniqueLines = new HashSet<>();

		// Variable to keep track of the last added line for subsequent lines
		String lastAddedLine = null;

		// Iterate through each line
		for (int i = 0; i < lines.size(); i++) {
			String currentLine = lines.get(i);

			// For the first four lines, disallow duplicates
			if (i < 4) {
				// Check if the current line is already added
				if (firstFourUniqueLines.add(currentLine)) {
					outputLines.append(currentLine).append(System.lineSeparator());
				}
			} else {
				// For lines after the first four, allow all lines including duplicates
				outputLines.append(currentLine).append(System.lineSeparator());
			}
		}

		// Write the merged output back to the same file
//		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
//			writer.write(outputLines.toString());
//		}
        Files.write(Paths.get(filePath), outputLines.toString().getBytes(StandardCharsets.UTF_8));

		

	}
}
