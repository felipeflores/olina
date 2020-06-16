package io.github.felipeflores.exception;

public class CoreException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CoreException(String message) {
		super(message);
	}

	public CoreException(String message, Throwable cause) {
		super(message, cause);
	}

}
