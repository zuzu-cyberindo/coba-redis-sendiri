package com.zuzu.redis.coba_sendiri.coba_redis_sendiri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CobaRedisSendiriApplication {

	public static void main(String[] args) {
		SpringApplication.run(CobaRedisSendiriApplication.class, args);
	}

}
