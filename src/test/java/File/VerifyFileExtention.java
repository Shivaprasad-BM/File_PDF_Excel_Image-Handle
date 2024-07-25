package File;

import java.io.File;
import java.util.Objects;

import org.testng.annotations.Test;

public class VerifyFileExtention {
	@Test
	void filextention() {
		String folderPath = "C:\\Users\\user\\Desktop\\";
		File lastModifiedFile = null;
		File[] listOfFiles = new File(folderPath).listFiles();
		if (listOfFiles.length == 0 || Objects.isNull(listOfFiles)) {
			System.out.println("Failed");
		} else {

			lastModifiedFile = listOfFiles[0];
			for (int i = 1; i < listOfFiles.length; i++) {
		//		System.out.println(lastModifiedFile+""+lastModifiedFile.lastModified());

				if (lastModifiedFile.lastModified() < listOfFiles[i].lastModified()) {
					lastModifiedFile = listOfFiles[i];
				}
			}
		
			System.out.println(lastModifiedFile);
			System.out.println(lastModifiedFile.getPath());
			System.out.println(lastModifiedFile.getName());

		}
		// Verify if the file has the correct extension
		if (lastModifiedFile.getName().endsWith(".pdf")) {
			System.out.println("File has the correct extension.");
		} else {
			System.out.println("File does not have the correct extension.");
		}
	}
}
