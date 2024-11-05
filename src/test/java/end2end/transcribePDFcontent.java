package end2end;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class transcribePDFcontent {

	static String filePathString = "C:\\CIBC\\6249AF.pdf";
	static String Range = "1-5"; // Range(example:- all, 1, 1-5)

	// retun
	static String TranscribePDFPAth = "";

	public static String getFileNameWithoutExtension(String filePath) {
		Path path = Paths.get(filePath);

		// Extract the file name with extension
		String fileNameWithExtension = path.getFileName().toString();

		// Find the last dot to remove the extension
		int lastDotIndex = fileNameWithExtension.lastIndexOf('.');
		if (lastDotIndex == -1) { // No extension found
			return fileNameWithExtension; // Return the full name if no extension
		}

		// Return the substring without the extension
		return fileNameWithExtension.substring(0, lastDotIndex);
	}

	public static void main(String[] args) throws Throwable {
		try {

			String finaltxtPath = "C:\\CIBC\\extractedpageTextFinal.txt";
			TranscribePDFPAth = "C:\\CIBC\\" + getFileNameWithoutExtension(filePathString) + "Transcribe.txt";

			int loopCount = loopCount(filePathString);
//			String liString = "0, 1-6, 7-13, 14-19, 20-25, 26-30";
			String liString = RangeGeneratorByPageCount(filePathString);
			String list[] = liString.split(",");
			List<String> rangeList = new ArrayList(Arrays.asList(list));
			List pageTextFileList = new ArrayList();

			for (int i = 1; i <= loopCount; i++) {
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.addArguments("--start-maximized");
				WebDriver driver = new FirefoxDriver(firefoxOptions);
				driver.manage().window().maximize();
				driver.navigate().to("file:///" + filePathString + "#zoom=100");
				Thread.sleep(5000);
				Integer downTime = i * 5;
//				Integer downTime = (i * 5) - 1;

				if (i == 1) {
					downTime = downTime - 2;
				}
				for (int j = 1; j <= downTime; j++) {

					pressKey(KeyEvent.VK_N);
				}

				Robot robot = new Robot();
				StartRecorder();
				Thread.sleep(15000);
				// move control to pdf
				moveMouse(600, 200);
				Thread.sleep(1000);
				MouseClickOnPointer();
				Thread.sleep(5000);

				StartStopRecordering();
				Thread.sleep(5000);

				moveMouse(150, 300);
				Thread.sleep(1000);
				MouseClickOnPointer();
				Thread.sleep(5000);

				StartStopRecordering();
				Thread.sleep(5000);
				stopRecorder();

				presskeyNthTime(KeyEvent.VK_ALT, 5);

				driver.quit();

//			String extractedTxtFile = "C:\\CIBC\\extractedPDFText.txt";
				String extractedTxtFile = extractText();

				String extractedPageTextFile = "C:\\CIBC\\extractedpageText" + i + ".txt";
				String pageRange = rangeList.get(i);

				if (loopCount == 1) {

					copyFile(extractedTxtFile, extractedPageTextFile);
				} else {
					// capture in renage
					CapturePageContentinRange(extractedTxtFile, extractedPageTextFile, pageRange);
				}
				pageTextFileList.add(extractedPageTextFile);
			}

			combineTextFiles(pageTextFileList, finaltxtPath);

			if (Range.toLowerCase().contains("all")) {
				copyFile(finaltxtPath, TranscribePDFPAth);

			} else {
				CapturePageContentinRange(finaltxtPath, TranscribePDFPAth, Range);
			}

			Thread.sleep(1000);

			System.out.println("Successfully extracted headers from PDF");

		} catch (Exception e) {
			System.out.println("Failed to extract header " + e);
		}
	}

	public static void presskeyNthTime(int keycode, int count) throws InterruptedException {
		for (int i = 1; i <= count; i++) {
			pressKey(keycode);
		}
	}

	public static void pressTwokeyNthTime(int keycode1, int keycode2, int count) throws InterruptedException {
		for (int i = 1; i <= count; i++) {
			pressTwoKeys(keycode1, keycode2);
		}
	}

	public static String RangeGenerator(int loopCount) throws IOException {
//		int loopCount = 4; // Total number of ranges
		int rangeSize = 5; // Size of each range

		StringBuilder rangeString = new StringBuilder();
		rangeString.append("0, "); // Add the initial '0' first

		int start = 1; // Start the range from 1

		for (int i = 0; i < loopCount; i++) {
			int end = start + rangeSize - 1;
			rangeString.append(start).append("-").append(end).append(", ");
			start += rangeSize;
		}

		// Remove the trailing comma and space
		if (rangeString.length() > 0) {
			rangeString.setLength(rangeString.length() - 2);
		}

		System.out.println(rangeString.toString());
		return rangeString.toString();

	}

	public static String RangeGeneratorByPageCount(String pdfFilePath) throws IOException {

		PDDocument document = PDDocument.load(new File(pdfFilePath));
		int pageCount = document.getNumberOfPages();

//	        int pageCount = 13; // Total number of pages
		int rangeSize = 5; // Size of each range

		StringBuilder rangeString = new StringBuilder();
		rangeString.append("0, "); // Add the initial '0' first

		int start = 1; // Start the range from 1

		while (start <= pageCount) {
			int end = Math.min(start + rangeSize - 1, pageCount); // Ensure we don't exceed the total page count
			rangeString.append(start).append("-").append(end);

			start += rangeSize; // Move the start of the next range

			if (start <= pageCount) { // Only append a comma if more ranges are to be added
				rangeString.append(", ");
			}
		}

		System.out.println(rangeString.toString());
		return rangeString.toString();
	}

	public static void combineTextFiles(List<String> sourceFiles, String destFilePath) {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(destFilePath), StandardCharsets.UTF_8)) {
			for (String sourceFile : sourceFiles) {
				List<String> lines = Files.readAllLines(Paths.get(sourceFile), StandardCharsets.UTF_8);
				for (String line : lines) {
					writer.write(line);
					writer.newLine();
				}
			}
			System.out.println("Text combined and saved successfully.");
		} catch (IOException e) {
			System.err.println("Error during file processing: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void CapturePageContentinRange(String inputFilePath, String extractedDataFilePath,
			String RangeofPage) {

		int Range[] = null;
		StringBuilder pageSummary = new StringBuilder();

		try {
			String content = readFile(inputFilePath);
//			Pattern tablePattern = Pattern.compile("(Page.*?)(out of region|$)", Pattern.DOTALL);
//			Pattern tablePattern = Pattern.compile("(Page.*?out of region)", Pattern.DOTALL);

			Pattern tablePattern = Pattern.compile("(Page.*?(?:out of region|$))", Pattern.DOTALL);

			Matcher tableMatcher = tablePattern.matcher(content);

			int pageCount = 1;

			// Loop through each found table
			while (tableMatcher.find()) {
				String pageData = tableMatcher.group(1); // Extract table data between "table" and "out of table"
				// tableSummary.append(tableData).append("\n");
				if (RangeofPage.contains("-")) {
					String[] rangeParts = RangeofPage.split("-");
					Range = new int[] { Integer.parseInt(rangeParts[0].trim()),
							Integer.parseInt(rangeParts[1].trim()) };

					if (pageCount >= Range[0] && pageCount <= Range[1]) {

						pageSummary.append(pageData).append("\n");
					}
				} else {
					// Handle the single page case
					int singlePage = Integer.parseInt(RangeofPage.trim());
					if (pageCount == singlePage) {
						pageSummary.append(pageData).append("\n"); // Append the data for the single page
					}
				}
				pageCount++;
			}
//			System.out.println(pageSummary);
			writeFile(extractedDataFilePath, pageSummary.toString());
			System.out.println("Successfully captured content");

		} catch (Exception e) {
			System.out.println("Failed to capture content " + e);
		}

	}

	public static String readFile(String filePath) throws IOException {
		StringBuilder contentBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				contentBuilder.append(currentLine).append("\n");
			}
		}
		return contentBuilder.toString();
	}

	public static void writeFile(String filePath, String content) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
			writer.write(content);
		}
	}

	public static Integer loopCount(String pdfFilePath) {
		Integer returnValue = 0;

		try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
			int pageCount = document.getNumberOfPages();
			returnValue = getRangeValue(pageCount);
			System.out.println("No of pages in document is " + pageCount);
		} catch (IOException e) {
			System.out.println("Failed to find pages in document");
//			e.printStackTrace();
		}
		return returnValue;

	}

	public static int getRangeValue(int pageCount) {
		if (pageCount <= 5) {
			return 1;
		} else {
			return (int) Math.ceil((pageCount - 5) / 5.0) + 1;
		}
	}

	public static void pressKey(int keyCode) throws InterruptedException {
		try {
			Robot robot = new Robot();
			robot.keyPress(keyCode);
			Thread.sleep(100);
			robot.keyRelease(keyCode);
			System.out.println("Key pressed and released successfully.");
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public static void pressTwoKeys(int keyCode1, int keyCode2) throws InterruptedException {
		try {
			Robot robot = new Robot();
			robot.keyPress(keyCode1);
			robot.keyPress(keyCode2);
			Thread.sleep(100);
			robot.keyRelease(keyCode2);
			robot.keyRelease(keyCode1);
			System.out.println("Two keys pressed and released successfully.");
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public static void pressThreeKeys(int keyCode1, int keyCode2, int keyCode3) throws InterruptedException {
		try {
			Robot robot = new Robot();
			robot.keyPress(keyCode1);
			robot.keyPress(keyCode2);
			robot.keyPress(keyCode3);
			Thread.sleep(100);
			robot.keyRelease(keyCode3);
			robot.keyRelease(keyCode2);
			robot.keyRelease(keyCode1);
			System.out.println("Three keys pressed and released successfully.");
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	private static void StartRecorder() throws InterruptedException {
		pressThreeKeys(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_N);
		System.out.println("Recorder started");
	}

	private static void StartStopRecordering() throws InterruptedException {
		pressTwoKeys(KeyEvent.VK_ALT, KeyEvent.VK_S);
		System.out.println("int recording");
	}

	static void moveMouse(int targetX, int targetY) throws InterruptedException {
		try {
			System.setProperty("java.awt.headless", "false");
			Robot robot = new Robot();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int screenWidth = (int) screenSize.getWidth();
			int screenHeight = (int) screenSize.getHeight();

			if (targetX >= 0 && targetX <= screenWidth && targetY >= 0 && targetY <= screenHeight) {
				robot.mouseMove(targetX, targetY);
				System.out.println("Mouse moved to coordinates successfully.");
			} else {
				System.out.println("The specified coordinates are out of screen bounds.");
			}

		} catch (AWTException e) {
			System.out.println("Failed - Error creating Robot instance: " + e.getMessage());
		}
	}

	static void MouseClickOnPointer() {
		try {
			System.setProperty("java.awt.headless", "false");
			Robot robot = new Robot();
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(500);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

			System.out.println("Successfully clicked at the current mouse position.");

		} catch (Throwable e) {
			System.out.println("Failed to click on the current mouse position. " + e);
		}

	}

	static void stopRecorder() {
//		String command = "powershell.exe -ExecutionPolicy Bypass -File \"C:\\CIBC\\stopRecorder.ps1\"";
		String command = "powershell -Command \"Get-WmiObject -Query 'SELECT * FROM Win32_Process WHERE Name = \\\"nvda.exe\\\"' | ForEach-Object { $_.Terminate() }\"";

		executeCommand(command);
		System.out.println("Stoped the recorder");
	}

	/*
	 * static void executeCommandexe(String command) { try { Process process =
	 * Runtime.getRuntime().exec(command); process.waitFor();
	 * 
	 * System.out.println("Command executed successfully: " + command); } catch
	 * (IOException | InterruptedException e) { e.printStackTrace(); } }
	 */

	public static void executeCommand(String command) {
		try {
			ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
			Process process = processBuilder.start();
			process.waitFor();

			System.out.println("Command executed successfully: " + command);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void verifyFilePathAndCreate(String folderPath) throws IOException {
		File file = new File(folderPath);

		if (folderPath.endsWith(File.separator) || file.isDirectory()) {
			if (!file.exists()) {
				if (file.mkdirs()) {
					System.out.println("Directory created: " + file.getAbsolutePath());
				} else {
					throw new IOException("Failed to create directory: " + file.getAbsolutePath());
				}
			}
		}
		// If the path represents a file, ensure the parent directory exists
		else if (file.isFile() || !folderPath.endsWith(File.separator)) {
			File parentDir = file.getParentFile();
			if (parentDir != null && !parentDir.exists()) {
				if (parentDir.mkdirs()) {
					System.out.println("Parent directories created: " + parentDir.getAbsolutePath());
				} else {
					throw new IOException("Failed to create parent directories: " + parentDir.getAbsolutePath());
				}
			}

			// Optionally create an empty file if it doesn't exist
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("File created: " + file.getAbsolutePath());
				} else {
					throw new IOException("Failed to create file: " + file.getAbsolutePath());
				}
			}
		} else {
			throw new IllegalArgumentException("The specified path is not valid: " + folderPath);
		}
	}

	static String extractText() {
		String destinationPath = "C:\\CIBC\\extractedPDFText.txt";
		try {
			String userHome = System.getProperty("user.home");
			String nvdaPath = userHome + File.separator + "AppData" + File.separator + "Roaming" + File.separator
					+ "nvda" + File.separator + "NVDARecord.txt";
			String sourcePath = nvdaPath;

			verifyFilePathAndCreate(destinationPath);
			System.out.println("Folder or file path verified and ready.");

			copyFile(sourcePath, destinationPath);
			System.out.println("File copied to: " + destinationPath);

		} catch (Exception e) {
			System.err.println("Error during file copy: " + e.getMessage());
		}
		return destinationPath;

	}

	public static void copyFile(String sourceFilePath, String destinationFilePath) throws IOException {
		File sourceFile = new File(sourceFilePath);
		File destinationFile = new File(destinationFilePath);

		// Create input and output streams
		try (FileInputStream fis = new FileInputStream(sourceFile);
				FileOutputStream fos = new FileOutputStream(destinationFile)) {

			// Buffer for data transfer
			byte[] buffer = new byte[1024];
			int length;

			// Copy file content from source to destination
			while ((length = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
			System.out.println("File copied to: " + destinationFile);
		}

		catch (Exception e) {
			System.out.println("Failed to copy to: " + destinationFile);
		}

	}

	static String GetTextFromTextFile(String txtFilePath) {
		String FilePath = txtFilePath;
		String Content = "";

		try {
			Content = Files.readString(Paths.get(FilePath), StandardCharsets.UTF_8);
//			System.out.println("Copied content from text file is : " + Content);
		}

		catch (java.nio.charset.MalformedInputException e) {
			try {
				Content = Files.readString(Paths.get(FilePath), StandardCharsets.ISO_8859_1);
//				System.out.println("Copied content from text file is : " + Content);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("Not able to copy content from text file" + e);
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Not able to copy content from text file" + e);
		}
		return Content;

	}

	public static void main1(String[] args) throws InterruptedException, IOException {
		System.out.println(loopCount("C:\\CIBC\\6249AF.pdf"));
		System.out.println(RangeGeneratorByPageCount("C:\\CIBC\\6249AF.pdf"));
	}
}
