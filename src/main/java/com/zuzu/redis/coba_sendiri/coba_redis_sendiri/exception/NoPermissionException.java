package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.exception;

public class NoPermissionException extends CaughtException {
    public NoPermissionException(String message) {
        super(message, 403);
    }
}
