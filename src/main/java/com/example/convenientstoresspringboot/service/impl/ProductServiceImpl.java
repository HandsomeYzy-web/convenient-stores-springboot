package com.example.convenientstoresspringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.convenientstoresspringboot.mapper.ProductMapper;
import com.example.convenientstoresspringboot.pojo.entity.Product;
import com.example.convenientstoresspringboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product findById(Integer id) {
        return productMapper.selectById(id);
    }

    @Override
    public IPage<Product> findAll(Page<Product> page, String category) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        if (category != null && !category.isEmpty() && !category.equals("all")) {
            queryWrapper.eq("category", category); // 添加分类筛选条件
        }
        return productMapper.selectPage(page, queryWrapper); // 使用 MyBatis-Plus 的分页查询
    }

    @Override
    public void save(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void update(Product product) {
        productMapper.updateById(product); // 使用 MyBatis-Plus 的 updateById 方法
    }

    @Override
    public void deleteById(Integer id) {
        productMapper.deleteById(id);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        Product product = productMapper.selectById(id);
        if (product != null) {
            product.setStatus(status);
            productMapper.updateById(product);
        }
    }
}