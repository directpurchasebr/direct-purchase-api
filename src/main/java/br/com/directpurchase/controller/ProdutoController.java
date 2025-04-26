package br.com.directpurchase.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.directpurchase.excel.request.ProdutosExcelRequest;
import br.com.directpurchase.excel.request.ProdutosExcelTextRequest;
import br.com.directpurchase.excel.service.ExcelImportService;
import br.com.directpurchase.exception.APIException;
import br.com.directpurchase.service.ProdutoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ExcelImportService excelImportService;

	@PostMapping(path = "/produto/importaProdutosExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> importaProdutosExcel(@ModelAttribute ProdutosExcelRequest request)
			throws APIException {
		try {
			log.info("[{}] [{}] /produto/importaProdutosExcel", request.getFornecedorId(), request.getFile());

			InputStream is = request.getFile().getInputStream();
			return ResponseEntity.ok().body(excelImportService.importaExcel(request.getFornecedorId(), is));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@PostMapping(path = "/produto/importaProdutosExcelTest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> importaProdutosExcelTest(@RequestBody ProdutosExcelTextRequest bean)
			throws APIException {
		try {
			Integer fornecedorId = bean.getFornecedorId();
			File initialFile = new File(bean.getFilePath());
			InputStream is = new FileInputStream(initialFile);

			return ResponseEntity.ok().body(excelImportService.importaExcel(fornecedorId, is));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping(path = "/produto/listar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> listarProdutos() throws APIException {
		try {
			log.info("[] /produto/listar");

			return ResponseEntity.ok().body(produtoService.listarProdutos());
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping(path = "/produto/buscar/{descricao}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> buscaPorDescricao(@PathVariable("descricao") String descricao) throws APIException {
		try {
			log.info("[{}] /produto/buscar/", descricao);

			return ResponseEntity.ok().body(produtoService.buscaProdutos(descricao));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
