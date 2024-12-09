package apivision.security.exceptions;

import apivision.utils.Utils;

/**
 * Purpose: To handle exceptions in the API
 */
public class ApiException extends RuntimeException {
    private int code;
    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
