package File;

import java.io.File;
import java.io.IOException;

public class FileUtils {

	// Method to verify if a folder path or file path exists, and create it if it
	// doesn't
	public static void verifyFolderPath(String folderPath) throws IOException {
		File file = new File(folderPath);

		// If the path represents a directory, ensure the directory exists
		if (folderPath.endsWith(File.separator) || file.isDirectory()) {
			if (!file.exists()) {
				if (file.mkdirs()) {
					System.out.println("Directory created: " + file.getAbsolutePath());
				} else {
					throw new IOException("Failed to create directory: " + file.getAbsolutePath());
				}
			}
		}
		// If the path represents a file, ensure the parent directory exists
		else if (file.isFile() || !folderPath.endsWith(File.separator)) {
			File parentDir = file.getParentFile();
			if (parentDir != null && !parentDir.exists()) {
				if (parentDir.mkdirs()) {
					System.out.println("Parent directories created: " + parentDir.getAbsolutePath());
				} else {
					throw new IOException("Failed to create parent directories: " + parentDir.getAbsolutePath());
				}
			}

			// Optionally create an empty file if it doesn't exist
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("File created: " + file.getAbsolutePath());
				} else {
					throw new IOException("Failed to create file: " + file.getAbsolutePath());
				}
			}
		} else {
			throw new IllegalArgumentException("The specified path is not valid: " + folderPath);
		}
	}

	public static void main(String[] args) {
		try {
			// Example usage: pass the folder path or file path to the method
			String folderPath = "C:\\CIBC1\\extractedPDFText1.txt";
			verifyFolderPath(folderPath);

			// Proceed with file operations knowing that the path exists
			System.out.println("Folder or file path verified and ready.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
