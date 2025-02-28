package com.example.convenientstoresspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.convenientstoresspringboot.pojo.entity.AttributeValue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttributeValueMapper extends BaseMapper<AttributeValue> {
    void insertBatch(List<AttributeValue> attributeValues);
}
