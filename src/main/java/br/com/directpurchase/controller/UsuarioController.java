package br.com.directpurchase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.directpurchase.dto.UsuarioDto;
import br.com.directpurchase.exception.APIException;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping(path = "/usuario/salvar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar(@RequestBody UsuarioDto bean) throws APIException {
		log.info("[{}] /usuario/salvar/", bean);
		try {
			return ResponseEntity.ok().body(usuarioService.salvarUsuario(bean));
		} catch (ValidationException e) {
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping(path = "/usuario/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> get() throws APIException {
		try {
			return ResponseEntity.ok().body(usuarioService.get());
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
