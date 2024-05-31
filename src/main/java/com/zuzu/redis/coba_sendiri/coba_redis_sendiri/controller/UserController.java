package com.zuzu.redis.coba_sendiri.coba_redis_sendiri.controller;

import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.dto.BasicResponse;
import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.dto.request.RequestUserDTO;
import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.dto.response.ResponseUserDTO;
import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.exception.CaughtException;
import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.service.user.UserService;
import com.zuzu.redis.coba_sendiri.coba_redis_sendiri.util.Constant;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = Constant.API)
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    private static final String USER = "/user";
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @PostMapping(value = USER)
    public ResponseEntity<BasicResponse<String>> createUser(@Valid @RequestBody RequestUserDTO requestUserDTO){

        BasicResponse<String> response = new BasicResponse<>();
        HttpStatus status;
        try {
            userService.createUser(requestUserDTO);
            response.setSuccess("success" , "success");
            status = HttpStatus.OK;
        } catch (CaughtException ce) {
            response.setFailed(ce.getMessage());
            status = HttpStatus.valueOf(ce.getCode());
        } catch (Exception e) {
            logger.error(String.format("User failed to create user"));
            response.setFailed(e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(value = USER + "/byid/{userId}")
    public ResponseEntity<BasicResponse<ResponseUserDTO>> getUserById(@PathVariable("userId") long id){

        BasicResponse<ResponseUserDTO> response = new BasicResponse<>();
        HttpStatus status;
        try {
            ResponseUserDTO responseUserDTO = userService.getByIdUser(id);
            response.setSuccess(responseUserDTO, "success");
            status = HttpStatus.OK;
        } catch (CaughtException ce) {
            response.setFailed(ce.getMessage());
            status = HttpStatus.valueOf(ce.getCode());
        } catch (Exception e) {
            logger.error(String.format("User failed to get user by id : {id}"));
            response.setFailed(e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(value = USER + "/get")
    public ResponseEntity<BasicResponse<List<ResponseUserDTO>>> getAllUser(){

        BasicResponse<List<ResponseUserDTO>> response = new BasicResponse<>();
        HttpStatus status;
        try {
            List<ResponseUserDTO> responseUserDTO = userService.getAllUser();
            response.setSuccess(responseUserDTO, "success");
            status = HttpStatus.OK;
        } catch (CaughtException ce) {
            response.setFailed(ce.getMessage());
            status = HttpStatus.valueOf(ce.getCode());
        } catch (Exception e) {
            logger.error(String.format("User failed to get all user"));
            response.setFailed(e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping(value = USER + "/update/{userId}")
    public ResponseEntity<BasicResponse<String>> updateUser(@PathVariable("userId") long id,
                                                            @Valid @RequestBody RequestUserDTO requestUserDTO){

        BasicResponse<String> response = new BasicResponse<>();
        HttpStatus status;
        try {
            userService.updateUser(id, requestUserDTO);
            response.setSuccess("success", "success");
            status = HttpStatus.OK;
        } catch (CaughtException ce) {
            response.setFailed(ce.getMessage());
            status = HttpStatus.valueOf(ce.getCode());
        } catch (Exception e) {
            logger.error(String.format("User failed to update user"));
            response.setFailed(e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(value = USER + "/delete/{userId}")
    public ResponseEntity<BasicResponse<String>> deleteUser(@PathVariable("userId") long id){

        BasicResponse<String> response = new BasicResponse<>();
        HttpStatus status;
        try {
            userService.deleteUser(id);
            response.setSuccess("success", "success");
            status = HttpStatus.OK;
        } catch (CaughtException ce) {
            response.setFailed(ce.getMessage());
            status = HttpStatus.valueOf(ce.getCode());
        } catch (Exception e) {
            logger.error(String.format("User failed to delete user"));
            response.setFailed(e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(response);
    }
}