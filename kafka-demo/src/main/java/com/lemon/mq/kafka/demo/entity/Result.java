package com.lemon.mq.kafka.demo.entity;

import lombok.Data;

/**
 * @Author 李荣许
 * @create 2020/12/16
 */
@Data
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

    private Result() {}

    public static Result ok() {
        return setResult(0, "ok", null);
    }

    public static <T> Result<T> ok(T data) {
        return setResult(0, "ok", data);
    }

    public static Result error(Integer code, String msg) {
        return setResult(code, msg, null);
    }

    private static <T> Result<T> setResult(Integer code, String msg, T data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
