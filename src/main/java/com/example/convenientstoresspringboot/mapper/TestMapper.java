package com.example.convenientstoresspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.convenientstoresspringboot.pojo.entity.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestMapper extends BaseMapper<Test> {
    @Select("select name from test")
    public Test getName();
}
