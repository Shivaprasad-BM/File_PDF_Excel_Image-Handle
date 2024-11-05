package File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class copyFile {
	public static String copyFile(String sourceFilePath, String destinationFilePath) throws IOException {
		File sourceFile = new File(sourceFilePath);
		File destinationFile = new File(destinationFilePath);

		// Create input and output streams
		try (FileInputStream fis = new FileInputStream(sourceFile);
				FileOutputStream fos = new FileOutputStream(destinationFile)) {

			// Buffer for data transfer
			byte[] buffer = new byte[1024];
			int length;

			// Copy file content from source to destination
			while ((length = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
		}

		// Return the path of the copied file
		return destinationFile.getAbsolutePath();
	}

	public static void main(String[] args) throws Throwable {
		// Get the user's home directory
		String userHome = System.getProperty("user.home");

		// Construct the path to the NVDA folder in a generic way
		String nvdaPath = userHome + File.separator + "AppData" + File.separator + "Roaming" + File.separator + "nvda"
				+ File.separator + "NVDARecord.txt";

		// Now you can use nvdaPath in your code
		System.out.println("The NVDA path is: " + nvdaPath);

	//	String sourcePath = nvdaPath; // Replace with actual source file path
		String sourcePath = "C:\\Users\\user\\AppData\\Roaming\\nvda\\NVDARecord.txt"; // Replace with actual source file path
		String destinationPath = "C:\\CIBC\\try.txt";
		new FileUtils().verifyFolderPath(destinationPath);
		// Replace with actual destination file path
		
		try {
			String copiedFilePath = copyFile(sourcePath, destinationPath);
			System.out.println("File copied to: " + copiedFilePath);
		} catch (IOException e) {
			System.err.println("Error during file copy: " + e.getMessage());
		}
	}
}
