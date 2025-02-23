package com.example.convenientstoresspringboot.controller;

import com.example.convenientstoresspringboot.common.Result;
import com.example.convenientstoresspringboot.pojo.entity.Test;
import com.example.convenientstoresspringboot.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 */
@Slf4j
@RequestMapping("/test")
@RestController
public class TestController {
    @Autowired
    private TestService testService;
    @GetMapping
    public Result test(){
        log.info("test");
        Test test = testService.test();
        return Result.success(test);
    }
}
