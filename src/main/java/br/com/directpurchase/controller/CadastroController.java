package br.com.directpurchase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.directpurchase.auth.request.UsuarioRequest;
import br.com.directpurchase.auth.service.UsuarioService;
import br.com.directpurchase.exception.APIException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CadastroController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping(path = "/usuario/salvar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar(@RequestBody UsuarioRequest bean) throws APIException {
		try {
			return ResponseEntity.ok().body(usuarioService.salvarUsuario(bean));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@PostMapping(path = "/usuario/consultar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultar(@RequestBody UsuarioRequest bean) throws APIException {
		try {
			return ResponseEntity.ok().body(usuarioService.salvarUsuario(bean));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
