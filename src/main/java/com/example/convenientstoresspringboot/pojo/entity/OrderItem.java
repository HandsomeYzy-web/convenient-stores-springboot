package com.example.convenientstoresspringboot.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("order_items")
public class OrderItem {
    @TableId(type = IdType.AUTO)
    private Integer id; // 订单项ID
    private Integer orderId; // 订单ID
    private Integer productId; // 商品ID
    private Integer quantity; // 商品数量
    private BigDecimal price; // 商品单价
}