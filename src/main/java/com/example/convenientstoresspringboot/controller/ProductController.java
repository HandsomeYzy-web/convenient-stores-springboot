package com.example.convenientstoresspringboot.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.convenientstoresspringboot.common.Result;
import com.example.convenientstoresspringboot.pojo.entity.Product;
import com.example.convenientstoresspringboot.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/info")
    public Result findById(@RequestParam Integer id) {
        return Result.success(productService.findById(id));
    }

    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer limit,
                          @RequestParam(required = false) String category) { // 添加分类参数
        // 使用 MyBatis-Plus 的分页功能
        Page<Product> pageParam = new Page<>(page, limit);
        IPage<Product> productPage = productService.findAll(pageParam, category); // 传递分类参数

        // 构建返回的数据结构
        return Result.success(productPage);
    }

    @PostMapping
    public Result save(@RequestBody Product product) {
        productService.save(product);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Product product) {
        productService.update(product);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        productService.deleteById(id);
        return Result.success();
    }

    @PutMapping("/status")
    public Result updateStatus(@RequestParam Integer id, @RequestParam Integer status) {
        log.info("id: {}, status: {}", id, status);
        if (id == null || status == null) {
            return Result.error("参数错误");
        }
        productService.updateStatus(id, status);
        return Result.success();
    }
}