package com.example.convenientstoresspringboot.controller;

import com.example.convenientstoresspringboot.common.Result;
import com.example.convenientstoresspringboot.pojo.dto.AttrInfoDTO;
import com.example.convenientstoresspringboot.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attributes")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @GetMapping("/list")
    public Result getAttributes(@RequestParam Long categoryId) {
        List<AttrInfoDTO> attributes = attributeService.getAttributesByCategoryId(categoryId);
        return Result.success(attributes);
    }

    @PostMapping("/save")
    public Result saveAttr(@RequestBody AttrInfoDTO attrInfoDTO) {
        attributeService.saveAttr(attrInfoDTO);
        return Result.success("属性添加成功");
    }

    @DeleteMapping("/delete")
    public Result deleteAttr(@RequestParam Long attrId) {
        attributeService.deleteAttr(attrId);
        return Result.success("属性删除成功");
    }
}