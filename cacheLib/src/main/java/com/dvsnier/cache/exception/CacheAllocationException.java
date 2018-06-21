package com.dvsnier.cache.exception;

/**
 * Created by dovsnier on 2018/6/12.
 */
public class CacheAllocationException extends RuntimeException {

    public CacheAllocationException() {
        super();
    }

    public CacheAllocationException(String message) {
        super(message);
    }

    public CacheAllocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheAllocationException(Throwable cause) {
        super(cause);
    }
}
