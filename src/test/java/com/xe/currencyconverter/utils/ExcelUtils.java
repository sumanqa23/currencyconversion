package com.xe.currencyconverter.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	public static Map<Integer, Map<String, String>> getData(String path, String sheetName) throws IOException{

		Map<Integer,Map<String,String>> data = new LinkedHashMap<Integer,Map<String,String>>();

		//Get access to the workbook
		FileInputStream fis = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		//Get the sheet you want
		XSSFSheet sheet = workbook.getSheet(sheetName);

		DataFormatter df = new DataFormatter();

		Iterator<Row> rows = sheet.iterator();

		int rowNo = 0;
		while(rows.hasNext()) {

			//Get the row
			Row row = rows.next();

			//Cell with String value
			Iterator<Cell> cells = row.iterator();

			if(rowNo != 0) {
				Map<String, String> eachRowData = new LinkedHashMap<String,String>();
				int cellNo = 0;
				while(cells.hasNext())
				{
					//Get the Cell
					Cell cell = cells.next();
					String cellvalue = df.formatCellValue(cell);
					eachRowData.put(df.formatCellValue(sheet.getRow(0).getCell(cellNo)), cellvalue);
					cellNo++;
				}
				data.put(rowNo, eachRowData);
			}
			rowNo++;
		}
		workbook.close();
		fis.close();

		return data;
	}
}
