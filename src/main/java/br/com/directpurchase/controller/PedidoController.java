package br.com.directpurchase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.directpurchase.dto.PedidoDto;
import br.com.directpurchase.exception.APIException;
import br.com.directpurchase.exception.ValidationException;
import br.com.directpurchase.request.ConsultaPedido;
import br.com.directpurchase.service.PedidoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping(path = "/pedido/salvar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> salvar(@RequestBody PedidoDto request) throws APIException {
        try {
            log.info("[{}] /pedido/salvar", request);

            return ResponseEntity.ok().body(pedidoService.salvarPedido(request));
        } catch (ValidationException | RuntimeException e) {
            log.error("[{}] {}", e.getMessage(), e);
            throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(path = "/pedido/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listar() throws APIException {
        try {
            log.info("[] /pedido/listar");

            return ResponseEntity.ok().body(pedidoService.listarPedidos());
        } catch (Exception e) {
            log.error("[{}] {}", e.getMessage(), e);
            throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(path = "/pedido/listarPedidosFornecedor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listarPedidosFornecedor() throws APIException {
        try {
            log.info("[] /pedido/listarPedidosFornecedor");

            return ResponseEntity.ok().body(pedidoService.listarPedidosPorFornecedor());
        } catch (Exception e) {
            log.error("[{}] {}", e.getMessage(), e);
            throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping(path = "/pedido/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> buscar(@RequestBody ConsultaPedido request) throws APIException {
        try {
            log.info("[{}] /pedido/buscar", request);

            return ResponseEntity.ok().body(pedidoService.consultarPedidos(request));
        } catch (ValidationException | RuntimeException e) {
            log.error("[{}] {}", e.getMessage(), e);
            throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping(path = "/pedido/consultarPedidosFornecedor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> consultarPedidosFornecedor(@RequestBody ConsultaPedido request) throws APIException {
        try {
            log.info("[{}] /pedido/consultarPedidosFornecedor", request);

            return ResponseEntity.ok().body(pedidoService.consultarPedidosPorFornecedor(request));
        } catch (ValidationException | RuntimeException e) {
            log.error("[{}] {}", e.getMessage(), e);
            throw new APIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
