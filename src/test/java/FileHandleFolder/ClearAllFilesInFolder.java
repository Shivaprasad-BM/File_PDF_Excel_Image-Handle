package FileHandleFolder;

import java.io.File;
import java.nio.file.Paths;

public class ClearAllFilesInFolder {

	public static void main(String[] args) {

		String folderPath = "C:\\Users\\user\\Desktop\\testfolder";
		File folder = new File(folderPath);

		// Check if the folder exists
		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles();

			if (files != null) {
				for (File file : files) {
					if (file.isFile()) {
						if (file.delete()) {
							System.out.println("Deleted: " + file.getName());
						} else {
							System.out.println("Failed to delete: " + file.getName());
						}
					}
				}
			}
		} else {
			System.out.println("The specified folder does not exist or is not a directory.");
		}
		System.out.println("sucess");
	}

}
