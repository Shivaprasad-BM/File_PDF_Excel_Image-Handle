package Adobe;

import java.io.IOException;

public class OpenPDFWithAdobe {
	public static void main(String[] args) {
		// Path to the Adobe Acrobat executable
		String adobePath = "C:\\Program Files\\Adobe\\Acrobat DC\\Acrobat\\Acrobat.exe";
		// Path to the PDF file
		String pdfPath = "C:\\CIBCnew\\80 (1).pdfx";

		// Call the method to open Adobe with the PDF
		openPDF(adobePath, pdfPath);
	}

	public static void openPDF(String adobePath, String pdfPath) {
		try {
			// Use ProcessBuilder to start Adobe Acrobat
			 ProcessBuilder processBuilder = new ProcessBuilder(adobePath, pdfPath);
		//	ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start", "/MAX", adobePath, pdfPath);

			processBuilder.start();

			// Continue with other automation tasks here
			System.out.println("Adobe Acrobat has been launched. Continuing with automation...");

			// Add your automation logic here
			// For example: perform actions in Acrobat using Selenium or another tool

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
