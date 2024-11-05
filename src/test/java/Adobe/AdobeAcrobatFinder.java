package Adobe;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.common.io.Files;

public class AdobeAcrobatFinder {

	public static void main(String[] args) {
		String adobeAcrobatPath = findAdobeAcrobatInstallationPath();
		if (adobeAcrobatPath != null && new File(adobeAcrobatPath).exists()) {
			System.out.println("Adobe Acrobat installation path: " + adobeAcrobatPath);
		} else {
			System.out.println("Adobe Acrobat is not installed.");
		}
	}

	private static String findAdobeAcrobatInstallationPath() {
		String command = "reg query HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall /s | findstr /i \"Adobe Acrobat\"";
		StringBuilder result = new StringBuilder();

		try {
			ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				// Extract the installation path from the registry output
				if (line.contains("InstallLocation")) {
					// Get the path after "InstallLocation" and trim any whitespace
					String path = line.substring(line.indexOf("REG_SZ") + 7).trim();
					// Append the executable name
					return path + File.separator + "Acrobat" + File.separator + "Acrobat.exe";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null; // Return null if Adobe Acrobat is not found
	}
}
