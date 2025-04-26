package br.com.directpurchase.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.directpurchase.auth.service.AuthService;
import br.com.directpurchase.exception.APIException;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.request.LoginRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthController {

	@Autowired
	private AuthService loginService;

	@PostMapping(path = "/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> login(@RequestBody LoginRequest request) throws APIException, ValidationException {
		try {
			log.info("[{}] /login", request);
			return ResponseEntity.ok().body(loginService.logar(request));

		} catch (ValidationException e) {
			return ResponseEntity.ok().body(e.getMessage());

		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping(path = "/auth/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> logout() throws APIException, ValidationException {
		try {
			log.info("[{}] /logout");
			return ResponseEntity.ok().body(loginService.logout());

		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping(path = "/auth/validateToken", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> validateToken() throws APIException, ValidationException {
		try {
			log.info("[{}] /validateToken");
			return ResponseEntity.ok().body(loginService.validateToken());

		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
