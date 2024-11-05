package FileHandleFolder;

import java.io.File;

public class GetfileNameInFilePath {
	public static void main(String[] args) {
		String filePath = "C:\\Users\\user\\Downloads\\Doc1 (1).pdf";
		String fileName = new File(filePath).getName();
	
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex == -1 || dotIndex == fileName.length() - 1) {

		}
		String extention = fileName.substring(dotIndex + 1);
		System.out.println(extention);
		
		String Name=fileName.substring(0, dotIndex);
		System.out.println(Name);
	}
}