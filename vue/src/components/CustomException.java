package com.ithaohao.common;

/**
 * @Author 浩浩
 * @Description 自定义业务异常类
 * @Since version-1.0
 */
public class CustomException extends RuntimeException{

        public CustomException(String message){
            super(message);
        }

}
