package br.com.directpurchase.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(APIException.class)
	public ResponseEntity<Object> handleApiException(APIException ex) {
		Map<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("status", ex.getStatus().value());
		body.put("error", ex.getError());
		body.put("clazz", ex.getClazz() != null ? ex.getClazz().getSimpleName() : "Desconhecido");

		return ResponseEntity.status(ex.getStatus()).body(body);
	}
}