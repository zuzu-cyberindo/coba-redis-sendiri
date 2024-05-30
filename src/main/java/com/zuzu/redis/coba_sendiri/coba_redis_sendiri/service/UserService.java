package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.service;

import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.dto.RequestUserDTO;
import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.dto.ResponseUserDTO;

import java.util.List;

public interface UserService {

    void createUser(RequestUserDTO dto);

    void updateUser(long id, RequestUserDTO dto);

    void deleteUser(long id);

    List<ResponseUserDTO> getAllUser();

    ResponseUserDTO getByIdUser (long id);
}
