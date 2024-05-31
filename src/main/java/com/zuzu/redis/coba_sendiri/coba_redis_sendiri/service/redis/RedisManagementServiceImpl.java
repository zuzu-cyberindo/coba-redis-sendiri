package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.service.redis;

import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.bean.RedisConnectionChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisManagementServiceImpl implements RedisManagementService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisConnectionChecker redisConnectionChecker;

    private static final Logger logger = LogManager.getLogger(RedisManagementServiceImpl.class);

    @Override
    public boolean activateRedis() {
        boolean isConnect = checkConnection();
        redisConnectionChecker.setConnectionClear(isConnect);
        return isConnect;
    }

    @Override
    public boolean checkConnection() {
        try {
            stringRedisTemplate.hasKey("test_key");
            return Boolean.TRUE;
        } catch (RedisConnectionFailureException exception) {
            return Boolean.FALSE;
        }
    }

    @Override
    public void setValueToRedis(String key, double score, String value) {
        try {
            Boolean hasKey = stringRedisTemplate.hasKey(key);
            BoundZSetOperations<String, String> zSetOps = stringRedisTemplate.boundZSetOps(key);
            if(Boolean.TRUE.equals(hasKey))
                zSetOps.removeRangeByScore(score, score);
            zSetOps.addIfAbsent(value, score);
            zSetOps.expire(30, TimeUnit.MINUTES);
        } catch (RedisConnectionFailureException exception) {
            logger.warn("Redis connection failure, closing the connection temporarily");
            redisConnectionChecker.setConnectionClear(Boolean.FALSE);
        } catch (Exception exception) {
            logger.warn("Un catch failed to save to Redis");
        }
    }

    @Override
    public void setValueToRedis(String key, String value) {
        if(redisConnectionChecker.isConnectionClear()) {
            try {
                BoundValueOperations<String, String> vOps = stringRedisTemplate.boundValueOps(key);
                vOps.set(value, 10, TimeUnit.MINUTES);
            } catch (RedisConnectionFailureException exception) {
                logger.warn("Redis connection failure, closing the connection temporarily");
                redisConnectionChecker.setConnectionClear(Boolean.FALSE);
            } catch (Exception exception) {
                logger.warn("Un catch failed to save to Redis");
            }
        }
    }

    @Override
    public void deleteValueFromRedis(String key, double score) {
        if(redisConnectionChecker.isConnectionClear()) {
            try {
                Boolean hasKey = stringRedisTemplate.hasKey(key);
                BoundZSetOperations<String, String> zSetOps = stringRedisTemplate.boundZSetOps(key);
                if(Boolean.TRUE.equals(hasKey))
                    zSetOps.removeRangeByScore(score, score);
            } catch (RedisConnectionFailureException exception) {
                logger.warn("Redis connection failure, closing the connection temporarily");
                redisConnectionChecker.setConnectionClear(Boolean.FALSE);
            } catch (Exception exception) {
                logger.warn("Un catch failed to save to Redis");
            }
        }
    }
}
