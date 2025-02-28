package com.example.convenientstoresspringboot.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("products")
public class Product {
    @TableId(type = IdType.AUTO)
    private Integer id;          // 商品ID
    private String name;         // 商品名称
    private String category;     // 商品分类
    private Integer stock;       // 库存数量
    private BigDecimal price;    // 单价
    private String supplierId;   // 供应商ID
    private String description;  // 商品描述
    private String image;        // 商品图片URL
    private Integer status;      // 状态（1: 上架, 0: 下架）
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
}