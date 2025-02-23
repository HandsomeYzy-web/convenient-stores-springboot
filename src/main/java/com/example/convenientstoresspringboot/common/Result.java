package com.example.convenientstoresspringboot.common;

import lombok.Data;

/**
 * 通用结果返回类
 */
@Data
public class Result {
    private Integer code; // 1表示成功，0表示失败
    private String msg; // 错误信息
    private Object data; // 返回数据

    public static Result success() {
        Result result = new Result();
        result.code = 1;
        result.msg = "success";
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.code = 1;
        result.msg = "success";
        result.data = data;
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.code = 0;
        result.msg = msg;
        return result;
    }
}
