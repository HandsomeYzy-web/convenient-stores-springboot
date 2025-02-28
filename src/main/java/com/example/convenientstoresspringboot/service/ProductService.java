package com.example.convenientstoresspringboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.convenientstoresspringboot.pojo.entity.Product;

public interface ProductService {
    Product findById(Integer id);
    IPage<Product> findAll(Page<Product> page, String category);
    void save(Product product);
    void update(Product product);
    void deleteById(Integer id);
    void updateStatus(Integer id, Integer status); // 添加更新状态方法
}