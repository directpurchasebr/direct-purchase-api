package br.com.directpurchase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.directpurchase.exception.APIException;
import br.com.directpurchase.service.FornecedorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FornecedorController {

	@Autowired
	private FornecedorService fornecedorService;

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

}
