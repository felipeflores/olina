package io.github.felipeflores.exception;

import java.util.HashMap;
import java.util.Map;

public class ObjectNotFoundException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException() {
		this("Object Not Found");
	}

	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
