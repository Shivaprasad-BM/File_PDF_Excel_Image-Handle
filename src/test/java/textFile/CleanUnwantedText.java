package textFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CleanUnwantedText {

	public static void main(String[] args) {

		// Define the file path
		String filePath = "C:\\CIBC\\extractedPDFText.txt";
		removeExtraline(filePath);

	}

	public static void removeExtraline(String filePath) {
		// Define the phrases to be removed
		String[] phrasesToRemove = { "Tools  button  collapsed", "no next heading", "no previous heading",
				"graphic    CIBC Logo", "Loading document..." };

		try {
			// Read the entire file content
			String content = new String(Files.readAllBytes(Paths.get(filePath)));

			// Remove the specified phrases
			for (String phrase : phrasesToRemove) {
				content = content.replace(phrase, "");
			}

			// Write the cleaned content back to the file
			Files.write(Paths.get(filePath), content.getBytes());

			System.out.println("Phrases removed and file updated successfully.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
