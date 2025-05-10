package br.com.directpurchase.controller;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.directpurchase.dto.FornecedorDto;
import br.com.directpurchase.excel.request.ImportaExcelRequest;
import br.com.directpurchase.excel.service.ExcelImportService;
import br.com.directpurchase.exception.APIException;
import br.com.directpurchase.service.FornecedorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FornecedorController {

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private ExcelImportService excelImportService;

	@PostMapping(path = "/fornecedor/importaExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> importaExcel(@ModelAttribute ImportaExcelRequest request)
			throws APIException {
		try {
			log.info("[{}] [{}] /fornecedor/importaExcel", request.getFornecedorId(), request.getFile());

			InputStream is = request.getFile().getInputStream();
			return ResponseEntity.ok().body(excelImportService.importaExcel(request.getFornecedorId(), is));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping(path = "/fornecedor/listar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> listarFornecedores() throws APIException {
		try {
			log.info("[] /fornecedor/listar");

			return ResponseEntity.ok().body(fornecedorService.listarFornecedores());
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@PostMapping(path = "/fornecedor/salvar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar(@ModelAttribute FornecedorDto request)
			throws APIException {
		try {
			log.info("[{}] /fornecedor/salvar", request);
			return ResponseEntity.ok().body(fornecedorService.salvarFornecedor(request));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
