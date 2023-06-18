package com.example.demo01.exception;

import com.example.demo01.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j//打印日志信息用
@ControllerAdvice//让系统知道这是处理异常的类
public class GlobalExceptionHandler {

    /**
     * 如果抛出的ServiceException，则调用该方法
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(),se.getMessage());
    }

}
