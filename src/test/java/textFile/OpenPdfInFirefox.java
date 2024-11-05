package textFile;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class OpenPdfInFirefox {
	public static void main(String[] args) {
		// Set the path to your geckodriver (ensure you have geckodriver installed)

		// Create a Firefox profile
//		FirefoxProfile profile = new FirefoxProfile();
//
//		// Set preferences to automatically open PDFs in Firefox using the built-in
//		// viewer
//		profile.setPreference("pdfjs.disabled", false);
//		profile.setPreference("browser.download.dir", "C:\\Users\\user\\Downloads");
//		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
//		profile.setPreference("browser.helperApps.neverAsk.openFile", "application/pdf");
//		profile.setPreference("pdfjs.previousHandler.alwaysAskBeforeHandling", false);

		// Load the profile into Firefox options
		FirefoxOptions options = new FirefoxOptions();
//		options.setProfile(profile);

		// Initialize Firefox WebDriver with the modified options
		WebDriver driver = new FirefoxDriver(options);

		// The path to your local PDF file
		String filePath = "C:\\Users\\user\\Downloads\\Headings.pdf";

		// Open the PDF with the 'view=Fit' parameter for page-fit mode
		String pdfUrl = filePath + "#view=Fit";

		// Open the PDF in the browser
		driver.get(pdfUrl);
	}
}
