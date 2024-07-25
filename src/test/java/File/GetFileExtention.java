package File;

public class GetFileExtention {
	public static void main(String[] args) {
		String fileName = "C:\\Users\\user\\Desktop\\kananans19"; // Replace with your file name
		String extension = "";
		if (fileName.lastIndexOf('.') != -1 && fileName.lastIndexOf('.') != 0) {
			extension = fileName.substring(fileName.lastIndexOf('.') +1);//+1 removes .dot
		}

		if (extension != "") {
			System.out.println("File extension: " + extension);
		} else {
			System.out.println("No file extension found.");
		}
	}

}
