package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
@NoArgsConstructor
public class BasicResponse<T> {
    private String message;
    private T content;
    private boolean isSuccess;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LinkedHashMap<String, Object> info;

    public void setSuccess(T content, String message) {
        this.content = content;
        this.message = message;
        this.isSuccess = Boolean.TRUE;
    }

    public void setFailed(String errorMessage) {
        this.message = errorMessage;
        this.isSuccess = Boolean.FALSE;
    }
}
