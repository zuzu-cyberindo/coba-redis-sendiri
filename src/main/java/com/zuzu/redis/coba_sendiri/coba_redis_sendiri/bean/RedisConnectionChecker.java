package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.bean;

import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.service.RedisManagementService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class RedisConnectionChecker {

    private boolean isConnectionClear;

    @Autowired
    private RedisManagementService redisManagementService;

    private static final Logger logger = LogManager.getLogger(RedisConnectionChecker.class);

    @PostConstruct
    public void init() {
        this.isConnectionClear = redisManagementService.checkConnection();
        logger.info(String.format("Connection to redis is %s", this.isConnectionClear ? "available" : "not available"));
    }


}
