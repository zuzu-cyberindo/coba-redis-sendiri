package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResponseUserDTO {

    private long id, dateJoin, dateCreated;

    private Long dateModified;

    private String firstName, lastName;

    private BigDecimal salary;

    private boolean active;
}
