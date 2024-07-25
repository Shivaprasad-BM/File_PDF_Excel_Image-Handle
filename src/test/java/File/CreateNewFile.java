package File;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateNewFile {
	@Test
	void checkandcreate() throws IOException {
		String folderPath = "C:\\Users\\user\\Desktop\\";
		String FileName = "kananans";
		String Extention = ".pdf";
		String FilePath = folderPath + FileName + Extention;
		File file = new File(FilePath);
		if (!file.exists()) {
			if (file.createNewFile()) {
				System.out.println("created");
			} else {
				System.out.println("failed to create");
			}
		} else {
			System.out.println("slready exist");
		}
		

	}
}
