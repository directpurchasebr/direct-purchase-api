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
		try (Workbook workbook = WorkbookFactory.create(file)) {
			Sheet sheet = workbook.getSheetAt(ExcelConstants.FIRST_SHEET);
			return processa(sheet);
		}
	}

	private Map<Integer, List<Object>> processa(Sheet sheet) {
		Map<Integer, List<Object>> data = new HashMap<>();
		int i = 0;
		for (Row row : sheet) {
			// Usar computeIfAbsent para garantir a lista
			List<Object> rowData = data.computeIfAbsent(i, k -> new ArrayList<>());
			for (Cell cell : row) {
				// Processa as células diretamente
				processCell(cell, rowData);
			}
			i++;
		}
		return data;
	}

	private void processCell(Cell cell, List<Object> rowData) {
		switch (cell.getCellType()) {
			case STRING:
				rowData.add(cell.getStringCellValue());
				break;
			case NUMERIC:
				rowData.add(cell.getNumericCellValue());
				break;
			// Adicionando casos em que você deseja ignorar ou tratar
			case BLANK:
			case FORMULA:
			case _NONE:
			case ERROR:
				break;
			default:
				rowData.add(cell.getRichStringCellValue());
		}
	}
}
