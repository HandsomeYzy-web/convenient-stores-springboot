package com.example.convenientstoresspringboot.service;

import com.example.convenientstoresspringboot.pojo.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategory1(); // 获取一级分类
    List<Category> getCategory2(Long category1Id); // 根据一级分类 ID 获取二级分类
    List<Category> getCategory3(Long category2Id); // 根据二级分类 ID 获取三级分类
}