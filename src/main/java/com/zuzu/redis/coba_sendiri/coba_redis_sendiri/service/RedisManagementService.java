package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.service;

public interface RedisManagementService {
    boolean activateRedis();

    boolean checkConnection();

    void setValueToRedis(String key, double score, String value);

    void setValueToRedis(String key, String value);

    void deleteValueFromRedis(String key, double score);
}
