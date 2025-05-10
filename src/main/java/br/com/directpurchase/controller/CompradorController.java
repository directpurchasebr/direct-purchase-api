package br.com.directpurchase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.directpurchase.dto.CompradorDto;
import br.com.directpurchase.exception.APIException;
import br.com.directpurchase.service.CompradorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CompradorController {

	@Autowired
	private CompradorService compradorService;

	@GetMapping(path = "/comprador/listar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> listarCompradores() throws APIException {
		try {
			log.info("[] /comprador/listar");

			return ResponseEntity.ok().body(compradorService.listarCompradores());
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@PostMapping(path = "/comprador/salvar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar(@ModelAttribute CompradorDto request)
			throws APIException {
		try {
			log.info("[{}] /fornecedor/salvar", request);
			return ResponseEntity.ok().body(compradorService.salvarComprador(request));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
