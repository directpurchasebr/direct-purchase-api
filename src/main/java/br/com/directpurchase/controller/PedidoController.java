package br.com.directpurchase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.directpurchase.exception.APIException;
import br.com.directpurchase.request.NovoPedidoRequest;
import br.com.directpurchase.service.PedidoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@PostMapping(path = "/pedido/salvar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvar(@RequestBody NovoPedidoRequest request) throws APIException {
		try {
			return ResponseEntity.ok().body(pedidoService.salvarPedido(request));
		} catch (Exception e) {
			log.error("[{}] {}", e.getMessage(), e);
			throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
