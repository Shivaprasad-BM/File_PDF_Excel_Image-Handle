package textFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class wsedfg {

	public static void main1(String[] args) {
		String line = "page 5  Introduction  heading level 2";

		String levelString = line.substring(line.indexOf("heading level") + 14, line.indexOf("heading level") + 15);
		System.out.println(levelString);
	}

	public static void main(String[] args) {
		String line = "page 5  Introduction  heading     level 2";

		// Define regex pattern to match "heading level" followed by the level number
		Pattern pattern = Pattern.compile("heading\\s+level\\s+(\\d+)");
		Matcher matcher = pattern.matcher(line);

	 	if (matcher.find()) {
			// Extract the level number
			String levelString = matcher.group(1);
			System.out.println(levelString);
		} else {
			System.out.println("No heading level found in the line.");
		}
	}
}
