package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.repository;

import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM user WHERE is_active = 1")
    List<User> userActive();
}
