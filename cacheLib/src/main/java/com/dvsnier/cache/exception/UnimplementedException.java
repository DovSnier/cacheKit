package com.dvsnier.cache.exception;

/**
 * UnimplementedException
 * Created by dovsnier on 2019-07-25.
 */
public class UnimplementedException extends RuntimeException {

    public UnimplementedException() {
    }

    public UnimplementedException(String message) {
        super(message);
    }

    public UnimplementedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnimplementedException(Throwable cause) {
        super(cause);
    }
}
