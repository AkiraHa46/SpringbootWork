package com.example.demo01.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口统一返回包装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private String code;
    private String msg;
    private Object data;

    public static <T> Result<T> success() {
        return new Result<T>("200", "", null);
    }

    public static <T> Result<T> success(Object data) {
        return new Result<T>("200", "", data);
    }

    public static <T> Result<T> error(String code, String msg) {
        return new Result<T>(code, msg, null);
    }

    public static <T> Result<T> error() {
        return new Result<T>("500", "系统错误", null);
    }

    public static <T> Result<T> error(String msg) {
        Result r = new Result();
        r.msg = msg;
        r.code = "600";
        return r;
    }

    public static <T> Result<T> success(Object data, String msg) {
        return new Result<T>(Constanes.CODE_200, msg, data);
    }
}