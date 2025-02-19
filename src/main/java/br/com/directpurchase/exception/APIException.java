package br.com.directpurchase.exception;

import org.springframework.http.HttpStatus;

public class APIException extends Exception {
	private static final long serialVersionUID = 1074801063534083301L;

	private final HttpStatus status;
	private final String error;
	private final Class<? extends Object> clazz;

	public APIException(final HttpStatus status, final String message) {
		super(message);

		this.status = status;
		this.error = message;
		this.clazz = null;
	}

	public APIException(final HttpStatus status, final String message, final Class<? extends Object> clazz) {
		super(message);

		this.status = status;
		this.error = message;
		this.clazz = clazz;
	}

	public APIException(final HttpStatus status, final String message, final String error) {
		super(message);

		this.status = status;
		this.error = error;
		this.clazz = null;
	}

	public APIException(final HttpStatus status, final String message, final String error,
			final Class<? extends Object> clazz) {
		super(message);

		this.status = status;
		this.error = error;
		this.clazz = clazz;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public Class<? extends Object> getClazz() {
		return clazz;
	}
}
