package com.example.convenientstoresspringboot.pojo.dto;

import lombok.Data;

@Data
public class AttrValueDTO {
    private Long id; // 属性值 ID
    private String valueName; // 属性值名称
    private Long attrId; // 属性键 ID
}