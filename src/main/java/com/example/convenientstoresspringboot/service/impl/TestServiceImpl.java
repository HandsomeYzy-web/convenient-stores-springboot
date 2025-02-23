package com.example.convenientstoresspringboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.convenientstoresspringboot.mapper.TestMapper;
import com.example.convenientstoresspringboot.pojo.entity.Test;
import com.example.convenientstoresspringboot.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    @Autowired
    private TestMapper testMapper;
    @Override
    public Test test() {
        return testMapper.getName();
    }
}
