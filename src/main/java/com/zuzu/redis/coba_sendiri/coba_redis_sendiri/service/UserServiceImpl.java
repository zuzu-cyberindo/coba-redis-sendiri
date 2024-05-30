package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.service;

import com.google.gson.Gson;
import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.bean.RedisConnectionChecker;
import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.dto.RequestUserDTO;
import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.dto.ResponseUserDTO;
import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.entity.User;
import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.exception.DataErrorException;
import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.repository.UserRepository;
import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisConnectionChecker redisConnectionChecker;
    @Autowired
    private RedisManagementService redisManagementService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static final String USER_KEY = "coba|redis";

    @Override
    public void createUser(RequestUserDTO dto) {

        User user = new User();
        user.setId(0);
        setBasic(user, dto);
        user.setActive(Boolean.TRUE);
        user.setDateCreated(new Date().getTime() / 1000L);
        userRepository.save(user);
        ResponseUserDTO responseUserDTO = convert(user);
        CompletableFuture.runAsync(() -> redisManagementService.setValueToRedis(USER_KEY, responseUserDTO.getId(), Constant.gson.toJson(responseUserDTO)));
    }

    @Override
    public void updateUser(long id, RequestUserDTO dto) {

        User user = getById(id);
        if (!user.isActive())
            throw new DataErrorException("User already deleted");

        setBasic(user, dto);
        user.setDateModified(new Date().getTime() / 1000L);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) {

        User user = getById(id);
        if (!user.isActive())
            throw new DataErrorException("User already deleted");

        user.setActive(Boolean.FALSE);
        user.setDateModified(new Date().getTime() / 1000L);
        userRepository.save(user);
    }

    @Override
    public List<ResponseUserDTO> getAllUser() {
        List<User> user = userRepository.userActive();
        return user.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public ResponseUserDTO getByIdUser(long id) {
        if(redisConnectionChecker.isConnectionClear()) {
            try {
                Boolean hasKey = stringRedisTemplate.hasKey(USER_KEY);
                BoundZSetOperations<String, String> zSetOps = stringRedisTemplate.boundZSetOps(USER_KEY);
                if(Boolean.TRUE.equals(hasKey)) {
                    long score = id;
                    Set<String> strings = zSetOps.rangeByScore(score, score);
                    if(!CollectionUtils.isEmpty(strings)){
                        System.out.println("ambil dari redis");
                        String jsobObject = strings.iterator().next();
                        return Constant.gson.fromJson(jsobObject, ResponseUserDTO.class);
                    }
                }
            } catch (RedisConnectionFailureException exception) {
                logger.warn("Redis connection failure, closing the connection temporarily");
                redisConnectionChecker.setConnectionClear(Boolean.FALSE);
            }
        }
        User user = getById(id);
        ResponseUserDTO responseUserDTO = convert(user);
        CompletableFuture.runAsync(() -> redisManagementService.setValueToRedis(USER_KEY, responseUserDTO.getId(), Constant.gson.toJson(responseUserDTO)));
        System.out.println("ambil dari db");
        return convert(user);
    }

    private ResponseUserDTO convert(User user){
        ResponseUserDTO dto = new ResponseUserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setSalary(user.getSalary());
        dto.setActive(user.isActive());
        dto.setDateJoin(user.getDateJoin());
        dto.setDateCreated(user.getDateCreated());
        dto.setDateModified(user.getDateModified());
        return dto;
    }

    private User getById(long id){
        Optional<User> byId = userRepository.findById(id);
        if(!byId.isPresent())
            throw new DataErrorException("User Not Found");
        return byId.get();
    }

    private void setBasic (User user, RequestUserDTO dto){
        BigDecimal bdSalary = dto.getSalary();
        user.setSalary(bdSalary);
        user.setDateJoin(dto.getDateJoin());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
    }
}
