package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    private static Workbook workbook;
    private static Sheet sheet;

    static {
        try {
            String path = "src/test/resources/excel/testData.xlsx"; // Excel dosya yolu
            FileInputStream inputStream = new FileInputStream(path);
            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheetAt(0); // İlk sayfa
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(colNum);
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return "";
        }
    }
}
