package com.example.convenientstoresspringboot.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局异常处理器
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理 DuplicateKeyException 异常
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("Duplicate key error: ", e);
        // 使用正则表达式从错误信息中提取字段名和重复值
        String message = e.getMessage();
        String fieldName = extractFieldName(message);
        String duplicateValue = extractDuplicateValue(message);

        if (fieldName != null && duplicateValue != null) {
            return Result.error(duplicateValue + "' 已存在");
        }
        return Result.error("数据库记录重复，操作失败！");
    }

    /**
     * 处理 RuntimeException 异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e) {
        log.error("Runtime error: ", e);
        return Result.error(e.getMessage());
    }

    /**
     * 其他异常的处理方法
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("Unknown error: ", e);
        return Result.error("服务器内部错误，请稍后再试！");
    }

    /**
     * 提取重复条目的字段名称
     * 例如：users.users_pk_2 -> pk_2 -> username
     */
    private String extractFieldName(String message) {
        Pattern pattern = Pattern.compile("Duplicate entry '.*' for key '(.*)'");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String key = matcher.group(1);
            if (key.contains("_")) {
                // 返回字段名：例如 users_pk_2  ->  pk_2 -> username
                return key.split("_")[1];
            }
        }
        return null;
    }

    /**
     * 提取重复条目的值
     * 例如：Duplicate entry '123' for key 'users.users_pk_2'
     */
    private String extractDuplicateValue(String message) {
        Pattern pattern = Pattern.compile("Duplicate entry '(.*)' for key");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
