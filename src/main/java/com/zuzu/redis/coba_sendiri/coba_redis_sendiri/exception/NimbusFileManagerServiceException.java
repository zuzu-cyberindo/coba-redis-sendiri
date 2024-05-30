package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.exception;

public class NimbusFileManagerServiceException extends CaughtException{
    public NimbusFileManagerServiceException(String message){
        super(message, 503);
    }
}
