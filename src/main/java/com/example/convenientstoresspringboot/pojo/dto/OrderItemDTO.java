package com.example.convenientstoresspringboot.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Integer id; // 订单项ID
    private Integer orderId; // 订单ID
    private Integer productId; // 商品ID
    private String productName; // 商品名称
    private String image; // 商品图片
    private BigDecimal price; // 商品单价
    private Integer quantity; // 商品数量
}