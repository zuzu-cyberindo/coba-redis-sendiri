package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class RequestUserDTO {

    @NotNull
    private String firstName, lastName;

    @NotNull
    private long dateJoin;

    @DecimalMin(value = "0.00", message = "Field 'nominal' must greater than 0.00")
    private BigDecimal salary;

    @NotNull
    private boolean isActive;
}
