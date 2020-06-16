package io.github.felipeflores.exception;

import java.util.HashMap;
import java.util.Map;

public class BusinessException extends CoreException {

    private static final long serialVersionUID = 1L;

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, Throwable cause){
        super(msg, cause);
    }

}

