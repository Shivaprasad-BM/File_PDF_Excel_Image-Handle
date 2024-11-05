package textFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset; // Use Charset instead of StandardCharsets
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class getTexFromTXT {
	static String filePath = "C:\\CIBC\\extractedPDFText.txt"; // Replace with your actual file path

	public static void main1(String[] args) {

		// Replace with the actual charset you determined from the file
		Charset charset = Charset.forName("ISO-8859-1"); // Change as needed

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), charset)) {
			StringBuilder content = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append(System.lineSeparator());
			}
			System.out.println(content.toString()); // Print the content of the file
		} catch (IOException e) {
			e.printStackTrace(); // Print the exception stack trace if an error occurs
		}
	}

	public static void main(String[] args) {

		try {
			byte[] bytes = Files.readAllBytes(Paths.get(filePath));
			String content = new String(bytes, StandardCharsets.UTF_8); // Attempt to read as UTF-8
			System.out.println(content); // Print the content of the file
		} catch (IOException e) {
			e.printStackTrace(); // Print the exception stack trace if an error occurs
		}
	}
}
