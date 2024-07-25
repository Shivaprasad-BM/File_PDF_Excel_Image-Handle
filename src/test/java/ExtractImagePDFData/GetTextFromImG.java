package ExtractImagePDFData;

import java.io.File;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.testng.annotations.Test;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;

public class GetTextFromImG {
	@Test
	void NavigateToApp() throws Throwable {
		String filePath = "C:\\Users\\user\\Downloads\\Screenshot 2024-07-25 200209.png";
		File outputfile = new File(filePath);
		ITesseract image1 = new Tesseract1();
		String Text = image1.doOCR(outputfile);
		System.out.println(Text);
	}

	@Test
	void getDateiNFormat() {

		String todayDate = new SimpleDateFormat("dd:MM:yy").format(new Date());
		System.out.println(todayDate);

	}

	@Test
	void dateBasedOnZoneTime() {

		TimeZone zoneTime = TimeZone.getTimeZone("America/New_York");

		SimpleDateFormat formater = new SimpleDateFormat("ddmmyy");
		formater.setTimeZone(zoneTime);
		String todayDate = formater.format(new Date());
		System.out.println(todayDate);

	}

}
