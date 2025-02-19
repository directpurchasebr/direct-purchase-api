package br.com.directpurchase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.directpurchase.dto.TemplateFour;
import br.com.directpurchase.dto.TemplateOne;
import br.com.directpurchase.dto.TemplateThree;
import br.com.directpurchase.dto.TemplateTwo;
import br.com.directpurchase.poi.ImportTamplateFour;
import br.com.directpurchase.poi.ImportTamplateOne;
import br.com.directpurchase.poi.ImportTamplateThree;
import br.com.directpurchase.poi.ImportTamplateTwo;
import br.com.directpurchase.poi.ImportaProdutosExcel;
import br.com.directpurchase.poi.Template;
import br.com.directpurchase.service.ExcelImportService;
import br.com.directpurchase.util.ValidateTemplate;

@SpringBootTest
public class ExcelIntegrationTest {

	private Logger log = LoggerFactory.getLogger(ExcelIntegrationTest.class);

	@Autowired
	private ImportaProdutosExcel importaProdutosExcel;

	@Autowired
	private ImportTamplateOne importTamplateOne;

	@Autowired
	private ImportTamplateTwo importTamplateTwo;

	@Autowired
	private ImportTamplateThree importTamplateThree;

	@Autowired
	private ImportTamplateFour importTamplateFour;

	@Autowired
	private ValidateTemplate validateTemplate;

	@Autowired
	private ExcelImportService excelImportService;

	@Test
	void executeTest() throws FileNotFoundException {
		String filePath = "C:\\Users\\julio.carmo\\Downloads\\template_4.xls";
		File initialFile = new File(filePath);
		InputStream targetStream = new FileInputStream(initialFile);
		excelImportService.importaExcel(1, targetStream);
	}

	@Deprecated
	void oldExecuteTest() throws EncryptedDocumentException, IOException {

		String filePath = "C:\\Users\\julio.carmo\\Downloads\\template_4.xls";
		File initialFile = new File(filePath);
		InputStream targetStream = new FileInputStream(initialFile);

		Map<Integer, List<Object>> test = importaProdutosExcel.readExcelFile(targetStream);
		Template template = validateTemplate.validate(test);

		if (template.equals(Template.ONE)) {
			List<TemplateOne> ones = importTamplateOne.convertTemplate(test);
			ones.stream().forEach(e -> {
				log.info("ONE: " + e);
			});
		}

		if (template.equals(Template.TWO)) {
			List<TemplateTwo> twos = importTamplateTwo.convertTemplate(test);
			twos.stream().forEach(e -> {
				log.info("TWO: " + e);
			});
		}
		if (template.equals(Template.THREE)) {
			List<TemplateThree> threes = importTamplateThree.convertTemplate(test);
			threes.stream().forEach(e -> {
				log.info("THREE: " + e);
			});
		}

		if (template.equals(Template.FOUR)) {
			List<TemplateFour> fours = importTamplateFour.convertTemplate(test);
			fours.stream().forEach(e -> {
				log.info("FOUR: " + e);
			});
		}
	}
}
