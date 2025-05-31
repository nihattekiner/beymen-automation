package utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
public class ExcelReader {
    public static String getExcelData(String filePath, int rowNumber, int columnNumber) {
        String cellValue = null;
        try {
            FileInputStream file = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowNumber);
            Cell cell = row.getCell(columnNumber);
            cellValue = cell.getStringCellValue();
            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cellValue;
    }
}
