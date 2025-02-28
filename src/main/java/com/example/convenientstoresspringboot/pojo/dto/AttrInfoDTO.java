package com.example.convenientstoresspringboot.pojo.dto;

import lombok.Data;
import java.util.List;

@Data
public class AttrInfoDTO {
    private Long id; // 属性键 ID
    private String attrName; // 属性键名称
    private Long categoryId; // 分类 ID
    private Integer categoryLevel; // 分类层级
    private List<AttrValueDTO> attrValueList; // 属性值列表
}