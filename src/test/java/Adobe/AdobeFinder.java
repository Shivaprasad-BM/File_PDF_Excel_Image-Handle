package Adobe;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdobeFinder {

    public static void main(String[] args) {
        String acrobatPath = findAdobeAcrobatInstallationPath();
        String readerPath = findAdobeReaderInstallationPath();

        if (acrobatPath != null && new File(acrobatPath).exists()) {
            System.out.println("Adobe Acrobat installation path: " + acrobatPath);
        } else if (readerPath != null && new File(readerPath).exists()) {
            System.out.println("Adobe Reader installation path: " + readerPath);
        } else {
            System.out.println("Neither Adobe Acrobat nor Adobe Reader is installed or paths are invalid.");
        }
    }

    private static String findAdobeAcrobatInstallationPath() {
        String command = "reg query HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall /s | findstr /i \"Adobe Acrobat\"";
        return findInstallationPath(command, "Acrobat.exe");
    }

    private static String findAdobeReaderInstallationPath() {
        String command = "reg query HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall /s | findstr /i \"Adobe Reader\"";
        return findInstallationPath(command, "AcroRd32.exe");
    }

    private static String findInstallationPath(String command, String executableName) {
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
                    // Append the executable name using File.separator
                    return path + File.separator + executableName; // Use File.separator here
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Return null if the application is not found
    }
}
