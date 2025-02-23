package com.example.convenientstoresspringboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.convenientstoresspringboot.pojo.entity.Test;

public interface TestService extends IService<Test> {
    Test test();
}
