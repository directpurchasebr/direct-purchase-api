package br.com.directpurchase.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.directpurchase.auth.request.LoginRequest;
import br.com.directpurchase.auth.service.LoginService;
import br.com.directpurchase.exception.APIException;
import br.com.directpurchase.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> logar(@RequestBody LoginRequest bean) throws APIException, ValidationException {

		try {
			log.info("[{}] /login", bean);
			return ResponseEntity.ok().body(loginService.logar(bean.getUsuario(), bean.getSenha()));

		} catch (ValidationException e) {
			return ResponseEntity.ok().body(e.getMessage());

		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
