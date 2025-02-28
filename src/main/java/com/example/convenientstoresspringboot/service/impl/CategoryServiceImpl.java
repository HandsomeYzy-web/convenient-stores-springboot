package com.example.convenientstoresspringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.convenientstoresspringboot.mapper.CategoryMapper;
import com.example.convenientstoresspringboot.pojo.entity.Category;
import com.example.convenientstoresspringboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategory1() {
        // 查询一级分类（level = 1）
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level", 1);
        return categoryMapper.selectList(queryWrapper);
    }

    @Override
    public List<Category> getCategory2(Long category1Id) {
        // 根据一级分类 ID 查询二级分类（parent_id = category1Id, level = 2）
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", category1Id).eq("level", 2);
        return categoryMapper.selectList(queryWrapper);
    }

    @Override
    public List<Category> getCategory3(Long category2Id) {
        // 根据二级分类 ID 查询三级分类（parent_id = category2Id, level = 3）
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", category2Id).eq("level", 3);
        return categoryMapper.selectList(queryWrapper);
    }
}