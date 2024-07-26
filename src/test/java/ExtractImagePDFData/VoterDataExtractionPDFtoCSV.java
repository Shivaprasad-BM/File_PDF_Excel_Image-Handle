package ExtractImagePDFData;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

public class VoterDataExtractionPDFtoCSV {
	public static String FolderPath = "C:\\Temp";
	public static String PdfFilePath = "C:\\Users\\user\\Downloads\\2024-FC-EROLLGEN-S10-100-FinalRoll-Revision2-ENG-232-WI.pdf";
	public static String csvFile = "C:\\Users\\user\\Downloads\\2024-FC-EROLLGEN-S13-269-FinalRoll-Revision2-MAR-1-WI.csv";

	@BeforeTest
	void CreateFolder() {
		File folder = new File(FolderPath);
		if (folder.exists() && folder.isDirectory()) {
			System.out.println("alreay exist");
			deleteAllFilesInFolder(FolderPath);
		} else {
			boolean created = folder.mkdir(); // Creates the directory
			if (created) {
				System.out.println("Folder created successfully");
			} else {
				System.out.println("Failed to create folder");
			}
		}
	}

	@Test
	public static void main() throws Throwable {
		double w = 6.7274, h = 2.8222, x, y;
		double intx = 0.4265;
		double inty = 0.8861;

		int Frompage = 3;
		// int toPage = new PDDocument().load(new File(PdfFilePath)).getNumberOfPages()
		// - 2;
		int toPage = 3;
		System.out.println(toPage);
		int col = 3;
		int row = 10;

		int number = 1;

		for (int i = Frompage; i <= toPage; i++) {

			y = inty;
			for (int j = 1; j <= row; j++) {

				x = intx;
				for (int k = 1; k <= col; k++) {
					System.out.println("row " + j + "col " + k + "  " + x + " " + y);
					// String fileName = "PdfSection" + new Random().nextInt(100) + ".PNG";
					// String filePath = Paths.get(FolderPath, new String[] { fileName
					// }).toString();
					String fileName = "PdfSection" + number + ".PNG";
					String filePath = Paths.get(FolderPath, new String[] { fileName }).toString();
					cropSection(w, h, x, y, i, filePath);
					String textDataString = gettext(filePath);
					List finalList = GetValidDataFromVoterID(textDataString);
					System.out.println(finalList);
					 writeDataToCSV(csvFile, finalList, i);
					x += 6.72;
					number++;
				}
				y += 2.7894;
			}
		}
	}

	@AfterTest(enabled = false)
	void deletedataFromFolder() {
		deleteAllFilesInFolder(FolderPath);
	}

	public static void cropSection(Double width_cm, Double height_cm, Double startX_cm, Double startY_cm,
			int PageNumber, String filePath) {
		try {
			int dpi = 300;
			int startX = cmToPixels(startX_cm, dpi);
			int startY = cmToPixels(startY_cm, dpi);
			int width = cmToPixels(width_cm, dpi);
			int height = cmToPixels(height_cm, dpi);
			captureScreenshotFromPdf(PdfFilePath, PageNumber, startX, startY, width, height, filePath);

		} catch (Exception e) {
		}
	}

	public static void captureScreenshotFromPdf(String filePath, int pageNumber, int startX, int startY, int width,
			int height, String outputFileStoragePath) throws IOException {
		PDDocument document = PDDocument.load(new File(filePath));

		PDPage page = document.getPage(pageNumber - 1);

		Rectangle rect = new Rectangle(startX, startY, width, height);

		PDFRenderer renderer = new PDFRenderer(document);
		BufferedImage image = renderer.renderImageWithDPI(pageNumber - 1, 300); // 300 is the DPI (dots per inch)

		BufferedImage croppedImage = image.getSubimage(rect.x, rect.y, rect.width, rect.height);

		File output = new File(outputFileStoragePath);
		ImageIO.write(croppedImage, "png", output);

		document.close();
	}

	public static int cmToPixels(double cm, int dpi) {
		return (int) Math.round(cm * dpi / 2.54);
	}

	public static String gettext(String imagePath) throws Throwable {

		ITesseract tesseract = new Tesseract();
		tesseract.setDatapath("C:\\tessdata");
		tesseract.setLanguage("Devanagari");

		// Perform OCR on the image
		String text = tesseract.doOCR(new File(imagePath));

		// Print the extracted text (optional)
		System.out.println("Extracted Text: \n" + text);
		return text;

	}

	public static List GetValidDataFromVoterID(String text) {
		List dataList = new ArrayList();

		 text = text.replaceAll("[\\?|\\||\\]|\\[]", "");
		String[] lines = text.split("\n");
		String firstLine = lines[0].trim();
		firstLine=firstLine.replace("?", "");
		System.out.println(firstLine);
		String slNO = " ";
		String voterID = "";
		if (!firstLine.contains("Name")) {
			String[] firstLineParts = firstLine.split(" ");
			slNO = firstLineParts[0];
			System.out.println("SerialNu " + slNO);
		//	dataList.add(slNO);

			  for (String string : firstLineParts) {
		            if (string.length() == 10|| string.length() == 11) {
		                char fourthChar = string.charAt(3);
		                if (fourthChar == 'S') {
		                    // Replace 'S' with '5'
		                    StringBuilder modifiedString = new StringBuilder(string);
		                    modifiedString.setCharAt(3, '5');
		                    voterID = modifiedString.toString();
		                } else if (Character.isLetter(fourthChar)) {
		                    // Replace any other alphabet with an empty string
		                    StringBuilder modifiedString = new StringBuilder(string);
		                    modifiedString.deleteCharAt(3);
		                    voterID = modifiedString.toString();
		                } else {
		                    voterID = string;
		                }
		            } else {
		                voterID = " ";
		            }
			  }
			//dataList.add(voterID);
		}
		dataList.add(slNO);
		dataList.add(voterID);

		// System.out.println("voterId " + voterID);
		Pattern imageIdPattern = Pattern.compile("[|\\[]\\s*(\\d+)");
		Pattern namePattern = Pattern.compile("Name\\s*:\\s*(.+)");
		Pattern relativeNamePattern = Pattern.compile("(?:Fathers|Husbands|Mothers|spouse) Name\\s*:\\s*(.+)");
		Pattern houseNumberPattern = Pattern.compile("House Number\\s*:\\s*(.+)\\s*Photo");
		Pattern agePattern = Pattern.compile("Age\\s*:\\s*(\\d+)");
		Pattern genderPattern = Pattern.compile("Gender\\s*:\\s*(\\w+)");

		// Extract and print each field
		// System.out.println("Extracted Data:");

		// Extract Image ID
		Matcher matcher = imageIdPattern.matcher(text);
		/*
		 * if (matcher.find()) { System.out.println("Image ID: " + matcher.group(1));
		 * dataList.add(matcher.group(1)); }
		 * 
		 * // Extract ID matcher = idPattern.matcher(text); if (matcher.find()) {
		 * System.out.println("ID: " + matcher.group(1));
		 * dataList.add(matcher.group(1)); }
		 */

		// Extract Name
		matcher = namePattern.matcher(text);
		if (matcher.find()) {
			// System.out.println("Name: " + matcher.group(1));
			dataList.add(matcher.group(1));
		}

		// Extract Relative's Name (Father or Husband)
		matcher = relativeNamePattern.matcher(text);
		if (matcher.find()) {
			// System.out.println("Father's/Husband's Name: " + matcher.group(1));
			dataList.add(matcher.group(1));
		}

		// Extract House Number
		matcher = houseNumberPattern.matcher(text);
		if (matcher.find()) {
			// System.out.println("House Number: " + matcher.group(1));
			dataList.add(matcher.group(1));
		}

		// Extract Age
		matcher = agePattern.matcher(text);
		if (matcher.find()) {
			// System.out.println("Age: " + matcher.group(1));
			dataList.add(matcher.group(1));
		}

		// Extract Gender
		matcher = genderPattern.matcher(text);
		if (matcher.find()) {
			// System.out.println("Gender: " + matcher.group(1));
			dataList.add(matcher.group(1));
		}
		// System.out.println(dataList);
		return dataList;
	}

	void deleteAllFilesInFolder(String FolderPath) {
		try {
			File folder = new File(FolderPath);
			if (folder.exists() && folder.isDirectory()) {
				File[] files = folder.listFiles();

				if (files != null) {
					for (File file : files) {
						if (file.isFile()) {
							if (file.delete()) {
							} else {
								// log.info("Failed To Delete " + file.getName());
							}
						}
					}
				}
			} else {
				System.out.println("The specified folder does not exist or is not a directory");
			}
			System.out.println("Successfully all files Deleted in Expected Folder Path " + FolderPath);
		} catch (Exception e) {
			System.out.println("Failed to files Deleted in Expected Folder Path " + FolderPath);
		}
	}

	public static void writeDataToCSV(String csvFilePath, List<String> dynamicData, int PageNumbaer) {
		// Define headers inside the method
		String[] headers = { "s.no", "List part No", "State District", "Assembly", "Division no & name", "Voter id",
				"Name", "Husband Name", "House Number", "Age", "Gender", "Page Number" };

		// Static values
		String staticListPartNo = "1";
		String staticStateDistrict = "chitradurga";
		String staticAssembly = "100-hiruyur";
		String staticDivisionNoAndName = "Adivala form2";
		String page = String.valueOf(PageNumbaer);

		// Prepare data list
		List<String> data = Arrays.asList(quote(dynamicData.get(0) + ""), // s.no (dynamic)
				(staticListPartNo), // List part No (static)
				(staticStateDistrict), // State District (static)
				(staticAssembly), // Assembly (static)
				(staticDivisionNoAndName), // Division no & name (static)
				(dynamicData.get(1) + ""), // Voter id (dynamic)
				(dynamicData.get(2) + ""), // Name (dynamic)
				(dynamicData.get(3) + ""), // Husband Name (dynamic)
				(dynamicData.get(4) + ""), // House Number (dynamic)
				(dynamicData.get(5) + ""), // Age (dynamic)
				(dynamicData.get(6) + ""), // Gender (dynamic)
				(page) // Page Number (dynamic)
		);
		File csvFile = new File(csvFilePath);
		boolean isFileEmpty = !csvFile.exists() || csvFile.length() == 0;

		try (FileWriter writer = new FileWriter(csvFilePath, true)) { // true to append

			// Write to CSV file
			if (isFileEmpty) {
				// Write headers only if the file is empty
				String headerLine = String.join(",", headers);
				writer.write(headerLine + "\n");
			}

			// Write data
			String dataLine = String.join(",", data);
			writer.write(dataLine + "\n");

			System.out.println("CSV file created successfully with headers and data.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("An error occurred while writing the CSV file.");
		}
	}

	// Helper method to wrap data in double quotes
	public static String quote(String value) {
		return "\"" + value + "\"";
	}

	public static String quoteAsText(String value) {
		return "\"'" + value + "\"";
	}

	public static void hanleline(String text) {
		text = text.replaceAll("[\\[\\]|]", "");
		String[] lines = text.split("\n");
		String firstLine = lines[0].trim();
		System.out.println(firstLine);
		String[] firstLineParts = firstLine.split(" ");
		String slNO = firstLineParts[0];
		System.out.println("SerialNu " + slNO);

		String voterID = "";
		for (String string : firstLineParts) {
			if (string.length() == 10) {

				if (string.charAt(3) == 'S') {
					StringBuilder modifiedString = new StringBuilder(string);
					modifiedString.setCharAt(3, '5');
					voterID = modifiedString.toString();
				} else {
					voterID = string;
				}
			} else {
				voterID = " ";
			}
		}

		// System.out.println("voterId " + voterID);
	}
}
