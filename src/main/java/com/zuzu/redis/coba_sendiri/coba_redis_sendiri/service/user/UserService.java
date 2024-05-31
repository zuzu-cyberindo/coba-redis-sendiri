package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.service.user;

import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.dto.request.RequestUserDTO;
import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.dto.response.ResponseUserDTO;

import java.util.List;

public interface UserService {

    void createUser(RequestUserDTO dto);

    void updateUser(long id, RequestUserDTO dto);

    void deleteUser(long id);

    List<ResponseUserDTO> getAllUser();

    ResponseUserDTO getByIdUser (long id);
}
