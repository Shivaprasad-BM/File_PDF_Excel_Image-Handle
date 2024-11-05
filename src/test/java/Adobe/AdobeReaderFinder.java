package Adobe;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdobeReaderFinder {

    public static void main(String[] args) {
        String adobeReaderPath = findAdobeReaderInstallationPath();
        if (adobeReaderPath != null) {
            System.out.println("Adobe Reader installation path: " + adobeReaderPath);
        } else {
            System.out.println("Adobe Reader is not installed.");
        }
    }

    private static String findAdobeReaderInstallationPath() {
        String command = "reg query HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall /s | findstr /i \"Adobe Reader\"";
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
//                    return path + "\\AcroRd32.exe"; // Adobe Reader executable
                	return path + File.separator + "Acrobat"+ File.separator+"AcroRd32.exe";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Return null if Adobe Reader is not found
    }
}

