package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.exception;

public class AuthenticationException extends CaughtException {

    public AuthenticationException(String message) {
        super(message, 401);
    }
}
