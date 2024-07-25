package PDF_Using_Spire;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;

public class pdfread {

	public static void main(String[] args) {

		PdfDocument doc = new PdfDocument();
		String path = "C:\\Users\\user\\Downloads\\2024-FC-EROLLGEN-S10-100-FinalRoll-Revision2-ENG-232-WI.pdf";
		doc.loadFromFile(path);
		PdfPageBase page = doc.getPages().get(2);

		// String text= page.extractText();
		// String text = page.extractText(new Rectangle(300,120,700,200)); // dummy
		// coordinates
		String text = page.extractText(new Rectangle(12, 25, 187, 77)); // box coordinates
		// String text = page.extractText(new Rectangle(12.996, 25.9992, 187.9992,
		// 77.9976));

		System.out.println(text);
		String[] words = text.split("\\s+");
		System.out.println(words);
		System.out.println("---------------------------------------");
		// in = words.length;
		int i = 0;
		List l1 = new ArrayList();
		for (String w : words) {
			l1.add(w);
			System.out.println(w);
		}
		System.out.println(l1);
//			System.out.println("---------------------------------------");
//		
//			List l2=new ArrayList();
//			l2.add(l1.get(16));
//			l2.add(l1.get(l1.size()-2));
//			System.out.println(l2);

		System.out.println(l1.get(6).toString().trim());

	}

}
