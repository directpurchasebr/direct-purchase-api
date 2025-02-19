package br.com.directpurchase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.directpurchase.dto.UsuarioDto;
import br.com.directpurchase.exception.APIException;
import br.com.directpurchase.request.LoginRequest;
import br.com.directpurchase.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = { "login" })
@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	@ApiOperation(value = "Login", response = UsuarioDto.class)
	@PostMapping(path = "/login/logar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> logar(@RequestBody LoginRequest bean) throws APIException {
		try {
			return ResponseEntity.ok().body(loginService.logar(bean.getUsuario(), bean.getSenha()));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
