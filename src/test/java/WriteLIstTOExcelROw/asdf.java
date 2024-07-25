package WriteLIstTOExcelROw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class asdf {
	public static void main(String[] args) {
		String ExcelPath = "C:\\Users\\user\\Desktop\\Personal Info.xlsx";
		String SheetName = "Form Data";
		Integer rowIndex = 1;
		List list = new ArrayList();
		list.add("sdfc");
		list.add("sdfc");
		list.add("sdfc");
		list.add("sdfc");

		try {
			File file = new File(ExcelPath);
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheet(SheetName);
			sheet.createRow(rowIndex);
			for (int i = 0; i < list.size(); i++) {
				sheet.getRow(rowIndex).createCell(i).setCellValue(list.get(i).toString());
			}
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			Thread.sleep(1000);
			workbook.close();
			fos.close();
			System.out.println("ok");
		} catch (

		Exception e) {
			System.out.println("ok" + e);
		}
	}
}
