package br.com.directpurchase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.directpurchase.dto.ProdutoDto;
import br.com.directpurchase.exception.APIException;
import br.com.directpurchase.request.ConsultaProduto;
import br.com.directpurchase.service.ProdutoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

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

	@PostMapping(path = "/produto/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> buscar(@RequestBody ConsultaProduto request) throws APIException {
		try {
			log.info("[{}] /produto/buscar/", request);

			return ResponseEntity.ok().body(produtoService.buscar(request));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@PostMapping(path = "/produto/salvar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar(@RequestBody ProdutoDto request)
			throws APIException {
		try {
			log.info("[{}] /produto/salvar", request);
			return ResponseEntity.ok().body(produtoService.salvarProduto(request));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
