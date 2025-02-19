package br.com.directpurchase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.directpurchase.exception.APIException;
import br.com.directpurchase.request.UsuarioRequest;
import br.com.directpurchase.response.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = { "pedido" })
@RestController
public class PedidoController {

	@ApiOperation(value = "Registro de novo pedido", response = Status.class)
	@PostMapping(path = "/pedido/novo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar(@RequestBody UsuarioRequest bean) throws APIException {
		try {
			return ResponseEntity.ok().body(usuarioService.salvarUsuario(bean));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
