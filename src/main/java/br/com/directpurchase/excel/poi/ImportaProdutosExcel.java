package br.com.directpurchase.excel.poi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import br.com.directpurchase.util.ExcelConstants;

@Service
public class ImportaProdutosExcel {

	public Map<Integer, List<Object>> readExcelFile(InputStream file) throws EncryptedDocumentException, IOException {
		Workbook workbook = WorkbookFactory.create(file);
		Sheet sheet = workbook.getSheetAt(ExcelConstants.FIRST_SHEET);
		return processa(sheet);
	}

	private Map<Integer, List<Object>> processa(Sheet sheet) {
		Map<Integer, List<Object>> data = new HashMap<>();
		int i = 0;
		for (Row row : sheet) {
			data.put(i, new ArrayList<Object>());
			for (Cell cell : row) {
				switch (cell.getCellType()) {
				case STRING:
					data.get(i).add(cell.getStringCellValue());
					break;
				case NUMERIC:
					data.get(i).add(cell.getNumericCellValue());
					break;
				case BLANK:
					continue;
				case FORMULA:
					continue;
				case _NONE:
					continue;
				case ERROR:
					break;
				default:
					data.get(i).add(cell.getRichStringCellValue());
				}
			}
			i++;
		}
		return data;
	}

}
