package com.example.convenientstoresspringboot.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("attribute_key")
public class AttributeKey {
    @TableId(type = IdType.AUTO)
    private Long id; // 主键
    private String name; // 属性键名称
    private Long categoryId; // 关联的分类 ID
    private Date createdAt; // 创建时间
    private Date updatedAt; // 更新时间
}