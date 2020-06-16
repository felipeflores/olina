package io.github.felipeflores.exception;

public class SystemException extends CoreException {

    private static final long serialVersionUID = 1l;

    public SystemException(String msg) {
        super(msg);
    }

    public SystemException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
