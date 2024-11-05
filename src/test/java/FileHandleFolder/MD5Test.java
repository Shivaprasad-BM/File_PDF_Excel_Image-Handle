package FileHandleFolder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class MD5Test {

	public static void main(String[] args) {
		String filePath = "C:\\Users\\user\\Downloads\\Doc1.docx";
		String md5Checksum = "";
		try {
			InputStream input = new FileInputStream(filePath);
			byte[] buffer = new byte[1024];
			MessageDigest md5Hash = MessageDigest.getInstance("MD5");
			int numRead = 0;
			while (numRead != -1) {
				numRead = input.read(buffer);
				if (numRead > 0) {
					md5Hash.update(buffer, 0, numRead);
				}
			}
			input.close();

			byte[] md5Bytes = md5Hash.digest();
			for (int i = 0; i < md5Bytes.length; i++) {
				md5Checksum += Integer.toString((md5Bytes[i] & 0xff) + 0x100, 16).substring(1);
			}
			if (!md5Checksum.isEmpty()) {
				System.out.println(md5Checksum);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		

	}
}