package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.exception;

public class DataErrorException extends CaughtException {
    public DataErrorException(String message) {
        super(message, 400);
    }

    public DataErrorException(String message, int code) {
        super(message, code);
    }
}
