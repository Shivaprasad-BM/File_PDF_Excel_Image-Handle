package File;
import java.io.File;

public class LastModifiedfileInfolder {
	public static void main(String[] args) {
		String folderPath = "C:\\Users\\user\\Desktop\\"; 
		File folder = new File(folderPath);

		if (folder.isDirectory()) {
			File latestModifiedFile = null;
			long latestModifiedTime = Long.MIN_VALUE;

			for (File file : folder.listFiles()) {
				if (file.isFile() && file.lastModified() > latestModifiedTime) {
					latestModifiedFile = file;
					latestModifiedTime = file.lastModified();
				}
			}

			if (latestModifiedFile != null) {
				System.out.println("Latest modified file: " + latestModifiedFile.getName());
			} else {
				System.out.println("No files in the folder.");
			}
		} else {
			System.out.println("Invalid folder path.");
		}
	}
}
