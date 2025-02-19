package br.com.directpurchase.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.directpurchase.exception.APIException;
import br.com.directpurchase.request.ProdutosExcelRequest;
import br.com.directpurchase.request.ProdutosExcelTextRequest;
import br.com.directpurchase.response.ProdutoResponse;
import br.com.directpurchase.service.ExcelImportService;
import br.com.directpurchase.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = { "produtos" })
@RestController
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ExcelImportService excelImportService;

	@ApiOperation(value = "Importa Produtos Excel", response = Object.class)
	@PostMapping(path = "/produto/importaProdutosExcel", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> importaProdutosExcel(@RequestBody ProdutosExcelRequest bean) throws APIException {
		try {
			Integer fornecedorId = bean.getFornecedorId();
			InputStream is = bean.getFile().getInputStream();
			return ResponseEntity.ok().body(excelImportService.importaExcel(fornecedorId, is));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@ApiOperation(value = "Importa Produtos Excel Teste", response = Object.class)
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

	@ApiOperation(value = "Listar Produtos", response = ProdutoResponse[].class)
	@GetMapping(path = "/produto/listar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> listarProdutos() throws APIException {
		try {
			return ResponseEntity.ok().body(produtoService.listarProdutos());
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@ApiOperation(value = "Busca Produtos pela Descricao", response = ProdutoResponse[].class)
	@GetMapping(path = "/produto/buscar/{descricao}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> buscaPorDescricao(@PathVariable("descricao") String descricao) throws APIException {
		try {
			return ResponseEntity.ok().body(produtoService.buscaProdutos(descricao));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
