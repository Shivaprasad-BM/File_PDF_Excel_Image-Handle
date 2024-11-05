package Adobe;

import java.io.File;

public class AdobeFinderwithPath {
	public static void main(String[] args) {
		String acrobatPath = findAdobeAcrobatInstallationPath();
		String readerPath = findAdobeReaderInstallationPath();

		// Check and print the paths for Adobe Acrobat and Adobe Reader
		if (acrobatPath != null && new File(acrobatPath).exists()) {
			System.out.println("Adobe Acrobat installation path: " + acrobatPath);
		} else if (readerPath != null && new File(readerPath).exists()) {
			System.out.println("Adobe Reader installation path: " + readerPath);
		} else {
			System.out.println("Neither Adobe Acrobat nor Adobe Reader is installed or paths are invalid.");
		}
	}

	private static String findAdobeAcrobatInstallationPath() {
		// Define common paths for Adobe Acrobat
		String[] commonPaths = { "C:\\Program Files\\Adobe\\Acrobat DC\\Acrobat\\Acrobat.exe",
				"C:\\Program Files (x86)\\Adobe\\Acrobat DC\\Acrobat\\Acrobat.exe",
				"C:\\Program Files\\Adobe\\Acrobat 2020\\Acrobat\\Acrobat.exe",
				"C:\\Program Files (x86)\\Adobe\\Acrobat 2020\\Acrobat\\Acrobat.exe",
				"C:\\Program Files\\Adobe\\Acrobat XI\\Acrobat\\Acrobat.exe",
				"C:\\Program Files (x86)\\Adobe\\Acrobat XI\\Acrobat\\Acrobat.exe" ,
				"C:\\Program Files (x86)\\Adobe\\Reader 11.0\\Reader\\AcroRd32.exe"};

		return checkCommonPaths(commonPaths);
	}

	private static String findAdobeReaderInstallationPath() {
		// Define common paths for Adobe Reader
		String[] commonPaths = { "C:\\Program Files\\Adobe\\Acrobat Reader DC\\Reader\\AcroRd32.exe",
				"C:\\Program Files (x86)\\Adobe\\Acrobat Reader DC\\Reader\\AcroRd32.exe",
				"C:\\Program Files\\Adobe\\Reader 11.0\\Reader\\AcroRd32.exe",
				"C:\\Program Files (x86)\\Adobe\\Reader 11.0\\Reader\\AcroRd32.exe" };

		return checkCommonPaths(commonPaths);
	}

	private static String checkCommonPaths(String[] paths) {
		for (String path : paths) {
			File file = new File(path);
			if (file.exists()) {
				return path; // Return the first valid path found
			}
		}
		return null; // Return null if no paths are found
	}
}
