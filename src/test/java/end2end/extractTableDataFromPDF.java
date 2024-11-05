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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class extractTableDataFromPDF {

	static String filePathString = "C:\\CIBC\\6249AF.pdf";
	static String finaltxtPath = "C:\\CIBC\\extractedpageTextFinal.txt";
	static String extractedTableText = "C:\\CIBC\\extractedTableData.txt";
	static String tableContentFilePath = "C:\\CIBC\\extractedTableContent.txt";

	public static void main(String[] args) throws Throwable {
		StringBuilder returnData = new StringBuilder();

		try {

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

			String tableSummary = ExtractTableData(finaltxtPath, extractedTableText);

			CaptureTableContent(finaltxtPath, tableContentFilePath);

//			nlpResponseModel.setStatus(CommonConstants.pass);

			returnData.append(">> Extracted table raw content file path: " + tableContentFilePath + "\n   ");
			returnData.append(">> Extracted table data in format file path: " + extractedTableText + "\n   ");
			returnData.append(">> Table Summary: " + tableSummary + "\n    ");

			System.out.println("Successfully extracted table data from PDF " + returnData);
//			nlpResponseModel.setMessage("Successfully captured table data from pdf");

		} catch (Exception e) {
			System.out.println("Failed to extract table data from pdf" + e);
			returnData.setLength(0);

//			nlpResponseModel.setStatus(CommonConstants.fail);
//			nlpResponseModel.setMessage("Failed to capture table data from pdf " + e);
		}
	}

	public static String getFileNameWithoutExtension(String filePath) {
		Path path = Paths.get(filePath);
		String fileNameWithExtension = path.getFileName().toString();
		int lastDotIndex = fileNameWithExtension.lastIndexOf('.');
		if (lastDotIndex == -1) {
			return fileNameWithExtension;
		}

		return fileNameWithExtension.substring(0, lastDotIndex);
	}

	public static void pressKey(int keyCode) throws InterruptedException {
		try {
			Robot robot = new Robot();
			robot.keyPress(keyCode);
			Thread.sleep(100);
			robot.keyRelease(keyCode);
			System.out.println("Keys pressed: " + KeyEvent.getKeyText(keyCode));
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
			System.out
					.println("Keys pressed: " + KeyEvent.getKeyText(keyCode1) + " + " + KeyEvent.getKeyText(keyCode2));
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
			System.out.println("Keys pressed: " + KeyEvent.getKeyText(keyCode1) + " + " + KeyEvent.getKeyText(keyCode2)
					+ " + " + KeyEvent.getKeyText(keyCode3));
		} catch (AWTException e) {
			e.printStackTrace();
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

	private static void StartRecorder() throws InterruptedException {
		pressThreeKeys(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_N);
		System.out.println("Recorder started");
	}

	private static void openPDFinAdobe() {
		String adobePath = "C:\\Program Files\\Adobe\\Acrobat DC\\Acrobat\\Acrobat.exe";
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(adobePath, filePathString);
			processBuilder.start();
			System.out.println("Adobe Acrobat has been launched. Continuing with pdf");
		} catch (Exception e) {
			System.out.println("Failed to open Adobe Acrobat, Continuing with pdf");
		}
	}

	private static void maxAdobe() throws InterruptedException {
		pressTwoKeys(KeyEvent.VK_CONTROL, KeyEvent.VK_H);
		System.out.println("Maximized Adobe");
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

	public static void killAcrobatProcess() {
		String taskKillCommand = "taskkill /im Acrobat.exe /f";
		executeCommand(taskKillCommand);
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

	static void FileLineRewriter(String filePath, String specificText) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(filePath));
			int startLine = findStartLine(lines, specificText);

			if (startLine >= 0) {
				rewriteFile(filePath, lines, startLine);
				System.out.println("Successfully re write file based on specified text line");
			} else {
				System.out.println("Specific text not found in the file.");
			}
			// Write the lines back to the file starting from the determined start line

		} catch (Exception e) {
			System.err.println("Error reading the file: " + e.getMessage());

		}
	}

	private static int findStartLine(List<String> lines, String searchTerm) {
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).contains(searchTerm)) {
				return i; // Return the line where the specific text is found
			}
		}
		return -1; // Return -1 if the text is not found
	}

	private static void rewriteFile(String filePath, List<String> lines, int startLine) {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
			for (int i = startLine; i < lines.size(); i++) {
				writer.write(lines.get(i));
				writer.newLine();
			}
			System.out.println("File rewritten, starting from line: " + startLine);
		} catch (IOException e) {
			System.err.println("Error writing to the file: " + e.getMessage());
		}
	}

	static void removeUnwantedLine(String filePath, String inputPhrases) {

//		String[] phrasesToRemove = { "Tools  button  collapsed", "no next heading", "no previous heading",
//				"graphic    CIBC Logo", "Loading document...","button    CLEAR FORM CLEAR FORM" };

		String[] phrasesToRemove = inputPhrases.split(",");
		try {
			String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
			// Read the entire file content

			// Remove the specified phrases
			for (String phrase : phrasesToRemove) {
				content = content.replace(phrase, "");
			}
			Files.write(Paths.get(filePath), content.getBytes(StandardCharsets.UTF_8));

			System.out.println("Phrases removed and file updated successfully.");

		} catch (IOException e) {
			System.out.println("Failed to remove " + e);
		}

	}

	private static void RemoveSequentialDuplicates(String filePath) throws IOException {
		try {
			// Read all lines from the file
//			List<String> lines = Files.readAllLines(Paths.get(filePath));
			List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);

			// StringBuilder to hold the final output
			StringBuilder outputLines = new StringBuilder();

			// Set to track unique lines for the first four lines
			Set<String> firstFourUniqueLines = new HashSet<>();

			// Variable to keep track of the last added line for subsequent lines
			String lastAddedLine = null;

			// Iterate through each line
			for (int i = 0; i < lines.size(); i++) {
				String currentLine = lines.get(i);

				// For the first four lines, disallow duplicates
				if (i < 4) {
					// Check if the current line is already added
					if (firstFourUniqueLines.add(currentLine)) {
						outputLines.append(currentLine).append(System.lineSeparator());
					}
				} else {
					// For lines after the first four, allow all lines including duplicates
					outputLines.append(currentLine).append(System.lineSeparator());
				}
			}

			// Write the merged output back to the same file
//			try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
//				writer.write(outputLines.toString());
//			}
			Files.write(Paths.get(filePath), outputLines.toString().getBytes(StandardCharsets.UTF_8));

			System.out.println("Successfully Removed Sequential Duplicates");
		} catch (Exception e) {
			System.out.println("Error while Remove Sequential Duplicates");
		}

	}

	private static void FilebeforeLineRemover(String filePath, String specificText) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(filePath));
			int startLine = findStartLine1(lines, specificText);
			writeRemainingLines(lines, startLine, filePath);
			System.out.println("Successfully removed line based on text");
		} catch (Exception e) {
			System.out.println("Error while removing before lines based on text");
		}

	}

	private static int findStartLine1(List<String> lines, String specificText) {
		// Check the first four lines for the specific text
		for (int i = 0; i < Math.min(4, lines.size()); i++) {
			if (lines.get(i).contains(specificText)) {
				// Return the next line index to start writing
				return i + 1;
			}
		}
		// If not found, start from the beginning
		return 0;
	}

	private static void writeRemainingLines(List<String> lines, int startLine, String filePath) {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
			for (int i = startLine; i < lines.size(); i++) {
				writer.write(lines.get(i));
				writer.newLine();
			}
		} catch (Exception e) {
			System.err.println("Error writing to the file: " + e.getMessage());
		}
	}

	public static String ExtractTableData(String inputFilePath, String outputFilePath) {

		StringBuilder tableSummary = new StringBuilder();
		try {
			String content = readFile(inputFilePath);

			// Create a pattern to match tables (from "table" to "out of table")
			Pattern tablePattern = Pattern.compile("table(.*?)out of table", Pattern.DOTALL);
			Matcher tableMatcher = tablePattern.matcher(content);

			StringBuilder output = new StringBuilder();
			int tableCount = 1;

			// Loop through each found table
			while (tableMatcher.find()) {
				String tableData = tableMatcher.group(1); // Extract table data between "table" and "out of table"

				// Pattern to match rows
				Pattern rowPattern = Pattern.compile("row\\s*(\\d+)(.*?)(?=(row|out of table|$))", Pattern.DOTALL);
				Matcher rowMatcher = rowPattern.matcher(tableData);

				int rowCount = 0;
				int maxColumnCount = 0;

				// First pass to count rows and columns
				while (rowMatcher.find()) {
					rowCount++; // Count each row

					// Pattern to match columns within the row
					String rowData = rowMatcher.group(2).trim();

					if (!rowData.startsWith("column")) {
						rowData = "column 1  " + rowData.trim();
					}
					Pattern colPattern = Pattern.compile("column\\s*(\\d+)\\s*(.*?)(?=(column|row|out of table|$))",
							Pattern.DOTALL);
					Matcher colMatcher = colPattern.matcher(rowData);

					int columnCount = 0;
					while (colMatcher.find()) {
						columnCount++; // Count each column
					}

					// Track the maximum number of columns in any row
					if (columnCount > maxColumnCount) {
						maxColumnCount = columnCount;
					}
					// -->
//					if (rowData.contains("table")) {
//						rowCount--;
//					}
					if (rowData.contains("table")) {
						String[] words = rowData.split("\\s+"); // Split the row data into words

						for (String word : words) {
							if (word.equals("table")) {
								rowCount--;
								break; // Exit the loop after finding "table"
							}
						}
					}
				}

				// Output table summary with row and column counts
				output.append("Table ").append(tableCount).append(" with ").append(rowCount).append(" rows and ")
						.append(maxColumnCount).append(" columns\n");

				tableSummary.append("Table ").append(tableCount).append(" with ").append(rowCount).append(" rows and ")
						.append(maxColumnCount).append(" columns\n");

				// Reset the row matcher to process rows again
				rowMatcher.reset();
				int currentRowCount = 1;

				// Process each row again to extract and display the actual data
				while (rowMatcher.find()) {
					String rowData = rowMatcher.group(2).trim();
					if (rowData.isEmpty())
						continue; // Skip empty rows
//-->
//					if (rowData.contains("table")) {
//						output.append("  Row ").append(currentRowCount).append("\n");
//						currentRowCount--;
//					}
					boolean isTableStandalone = false;
					if (rowData.contains("table")) {
						String[] words = rowData.split("\\s+"); // Split the row data into words

						for (String word : words) {
							if (word.equals("table")) {
								output.append("  Row ").append(currentRowCount).append("\n");
								currentRowCount--;
								isTableStandalone = true;
								break; // Exit the loop after finding "table"
							}
						}
						if (!isTableStandalone) {
							output.append("  Row ").append(currentRowCount).append("\n");
						}
					}

					else {
						output.append("  Row ").append(currentRowCount).append("\n");
					}

					// output.append(" Row ").append(currentRowCount).append("\n");
					// Add "Column 1" for rows not starting with "column"
					if (!rowData.startsWith("column")) {
						rowData = "column 1  " + rowData.trim();
					}
					// Pattern to match columns within the row
					Pattern colPattern = Pattern.compile("column\\s*(\\d+)\\s*(.*?)(?=(column|row|out of table|$))",
							Pattern.DOTALL);
					Matcher colMatcher = colPattern.matcher(rowData);

					int columnCount = 1; // Start column counting from 1
					while (colMatcher.find()) {
						String columnData = colMatcher.group(2).trim();
						if (!columnData.isEmpty()) {
							// Output the current column data
							output.append("    Column ").append(columnCount).append(" ").append(columnData)
									.append("\n");
							columnCount++;
						}
					}

					currentRowCount++;
				}

				output.append("\n");
				tableCount++;
			}
			tableSummary.append("total table count " + (tableCount - 1));

			// Write the extracted table data to the output file
			writeFile(outputFilePath, output.toString());

			System.out.println("Successfully captured table data from file");
		} catch (Exception e) {
			System.out.println("Failed to capture table data from file" + e);
		}

		return tableSummary.toString();
	}

	public static String CaptureTableContent(String inputFilePath, String outputFilePath) {

		StringBuilder tableSummary = new StringBuilder();
		try {

			String content = readFile(inputFilePath);
			// Pattern tablePattern = Pattern.compile("table(.*?)out of table",
			// Pattern.DOTALL);
			Pattern tablePattern = Pattern
					.compile("\\btable\\b.*?(row\\s+\\d+\\s+column\\s+\\d+.*?)(?=\\bout of table\\b)", Pattern.DOTALL);

			Matcher tableMatcher = tablePattern.matcher(content);
			int tableCount = 1;

			while (tableMatcher.find()) {
				String tableData = tableMatcher.group(1); // Extract table data between "table" and "out of table"
				tableSummary.append(tableCount + ".\n").append("table ").append(tableData).append("\n");
				tableCount++;
			}
			writeFile(outputFilePath, tableSummary.toString());
			// System.out.println(tableSummary);
			System.out.println("Successfully captured table content");
		} catch (Exception e) {
			System.out.println("Failed to capture table content " + e);

		}
		return tableSummary.toString();
	}

	public static void main1(String[] args) throws InterruptedException, IOException {
		System.out.println(loopCount("C:\\CIBC\\6249AF.pdf"));
		System.out.println(RangeGeneratorByPageCount("C:\\CIBC\\6249AF.pdf"));
	}
}
