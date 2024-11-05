package FileHandleFolder;

import java.io.File;

class DeleteAllFilesInFolder {
	public static void main(String[] args) {
		String folderPath = "C:\\spencersChrome";
		try {
			File folder = new File(folderPath);
			if (!folder.exists()) {
				if (folder.mkdir()) {
					System.out.println("Folder created successfully: " + folderPath);
				} else {
					System.out.println("Failed to create folder: " + folderPath);
				}
			} else {
				if (folder.isDirectory()) {
					deleteFolderContents(folder);
					System.out.println("Successfully deleted all files inside the folder: " + folderPath);
				} else {
					System.out.println("The specified path is not a directory: " + folderPath);
					return;
				}
			}
		} catch (Exception e) {
			System.out.println("Failed to process the folder: " + folderPath);
			e.printStackTrace();
		}
	}

	private static void deleteFolderContents(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					deleteFolderContents(file); // Recursively delete subdirectories
				}
				if (!file.delete()) {
					System.out.println("Failed to delete: " + file.getAbsolutePath());
				}
			}
		}
	}
}
