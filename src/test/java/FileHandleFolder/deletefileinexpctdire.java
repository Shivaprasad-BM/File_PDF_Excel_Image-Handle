package FileHandleFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class deletefileinexpctdire {
	public static void main(String[] args) throws IOException {
		String fileName = "Doc1 (5)";
		String fileExtension = "docx";
	
		String homeDirectory = System.getProperty("user.home");
		System.out.println(homeDirectory);
		String downloaddir = homeDirectory + "/Downloads/";
		System.out.println(downloaddir);
		
		String filepath = downloaddir + fileName + "." + fileExtension;
		System.out.println(filepath);
		Path path = Paths.get(filepath);
		System.out.println(path);
		if (Files.exists(path)) {
			Files.delete(path);
		} else {
			// File not found
			System.out.println("File not found: " + filepath);
		}

	}
}
