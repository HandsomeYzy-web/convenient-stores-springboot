package com.example.convenientstoresspringboot.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 分类实体类
  */
@Data
public class Category {
    @TableId(type = IdType.AUTO)
    private Long id; // 主键
    private String name; // 分类名称
    private Long parentId; // 父分类 ID
    private Integer level; // 分类层级（1: 一级，2: 二级，3: 三级）
    private Date createdAt; // 创建时间
    private Date updatedAt; // 更新时间
}