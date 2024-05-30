package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.exception;

public class ProxyServiceException extends CaughtException {
    public ProxyServiceException(int code, String message) {
        super(message, code);
    }
}
