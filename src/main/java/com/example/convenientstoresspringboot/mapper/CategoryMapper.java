package com.example.convenientstoresspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.convenientstoresspringboot.pojo.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}