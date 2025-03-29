package br.com.directpurchase.exception;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1074801063534083301L;

	private final String error;
	private final Class<? extends Object> clazz;

	public ValidationException(final String message) {
		super(message);

		this.error = message;
		this.clazz = null;
	}

	public String getError() {
		return error;
	}

	public Class<? extends Object> getClazz() {
		return clazz;
	}

}
