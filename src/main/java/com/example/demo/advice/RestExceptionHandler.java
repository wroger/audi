package com.example.demo.advice;

import com.example.demo.limit.LoginException;
import com.example.demo.limit.RequestLimitException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    /**
     * 默认全局异常处理。
     * 
     * @Author:wroger
     * @param e the e
     * @return ResultData
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<String> exception(Exception e) {
        if (e instanceof RequestLimitException) {
            return ResultData.fail(ResultCode.RC500.getCode(), e.getMessage());
        }
        if (e instanceof LoginException) {
            return ResultData.fail(ResultCode.USERNAME_OR_PASSWORD_ERROR.getCode(), e.getMessage());
        }
        return ResultData.fail(ResultCode.RC999.getCode(), e.getMessage());
    }
}
