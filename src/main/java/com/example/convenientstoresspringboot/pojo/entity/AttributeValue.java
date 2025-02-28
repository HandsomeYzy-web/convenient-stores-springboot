package com.example.convenientstoresspringboot.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("attribute_value")
public class AttributeValue {
    @TableId(type = IdType.AUTO)
    private Long id; // 主键
    private String value; // 属性值
    private Long keyId; // 关联的属性键 ID
    private Date createdAt; // 创建时间
    private Date updatedAt; // 更新时间
}