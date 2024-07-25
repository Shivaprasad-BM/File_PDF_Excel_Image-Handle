package File;

import java.io.File;

import org.testng.annotations.Test;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateNewFolder {
	@Test

	public static void m() {

		String MainFolder = "C:\\Users\\user\\Desktop\\kkkaa";
		File directory = new File(MainFolder);
		if (!directory.exists()) {
			boolean success = directory.mkdir();
			// mkdir used to create single folder//directoryt -->exp->
			// "C:\\Users\\user\\Desktop\\kkkaa";
			// it will create last folder
			// mkdirs() user to create create parent and child -->
			// ex-->C:\\parent\\child\\newchild
			// it will create all folders
			if (success) {
				System.out.println("Success To Create ");
			} else {
				System.out.println("Failed to To Create ");
			}

		} else {
			System.out.println("Directory already exists.");
		}
	}
}
