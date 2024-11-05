package textFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileCombiner {

	public static void combineTextFiles(List<String> sourceFiles, String destFilePath) {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(destFilePath), StandardCharsets.UTF_8)) {
			for (String sourceFile : sourceFiles) {
				List<String> lines = Files.readAllLines(Paths.get(sourceFile), StandardCharsets.UTF_8);
				for (String line : lines) {
					writer.write(line);
					writer.newLine();
				}
			}
			System.out.println("Text combined and saved successfully.");
		} catch (IOException e) {
			System.err.println("Error during file processing: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// List of source file paths
		List<String> sourceFiles = Arrays.asList("C:\\Users\\user\\AppData\\Roaming\\nvda\\NVDARecord.txt",
				"C:\\CIBC\\Split\\extractedPDFText.txt","C:\\CIBC\\Split\\dest.txt");

		// Destination file path
		String destFilePath = "C:\\CIBC\\Split\\PDFText.txt";

		// Call the method to combine the files
		FileCombiner.combineTextFiles(sourceFiles, destFilePath);
	}
}