package end2end;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Paths;

public class wsedfcvg {
	static String adobePath = "C:\\Program Files\\Adobe\\Acrobat DC\\Acrobat\\Acrobat.exe";

	public static void main(String[] args) throws InterruptedException {

		pressTwoKeys(KeyEvent.VK_ALT, KeyEvent.VK_TAB);
	}

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

	static void stopRecorder() {
//		String command = "powershell.exe -ExecutionPolicy Bypass -File \"C:\\CIBC\\stopRecorder.ps1\"";
		String command = "powershell -Command \"Get-WmiObject -Query 'SELECT * FROM Win32_Process WHERE Name = \\\"nvda.exe\\\"' | ForEach-Object { $_.Terminate() }\"";

		executeCommand(command);
		System.out.println("Stoped the recorder");
	}

	public static void killAcrobatProcess(String adobename) {
		String taskKillCommand = "taskkill /im " + adobename + " /f";
		executeCommand(taskKillCommand);
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

}
