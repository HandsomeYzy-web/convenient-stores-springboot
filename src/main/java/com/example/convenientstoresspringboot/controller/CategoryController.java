package com.example.convenientstoresspringboot.controller;

import com.example.convenientstoresspringboot.common.Result;
import com.example.convenientstoresspringboot.pojo.entity.Category;
import com.example.convenientstoresspringboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取一级分类
     * @return
     */
    @GetMapping("/getCategory1")
    public Result getCategory1() {
        List<Category> category1List = categoryService.getCategory1();
        return Result.success(category1List);
    }

    /**
     * 获取二级分类
     * @param category1Id
     * @return
     */
    @GetMapping("/getCategory2")
    public Result getCategory2(@RequestParam Long category1Id) {
        List<Category> category2List = categoryService.getCategory2(category1Id);
        return Result.success(category2List);
    }

    /**
     * 获取三级分类
     * @param category2Id
     * @return
     */
    @GetMapping("/getCategory3")
    public Result getCategory3(@RequestParam Long category2Id) {
        List<Category> category3List = categoryService.getCategory3(category2Id);
        return Result.success(category3List);
    }
}