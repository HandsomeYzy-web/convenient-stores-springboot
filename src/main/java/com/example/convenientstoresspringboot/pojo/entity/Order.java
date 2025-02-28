package com.example.convenientstoresspringboot.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("orders")
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer id; // 订单ID
    private String memberId; // 会员ID
    private String employeeId; // 操作员工ID
    private BigDecimal totalAmount; // 订单总金额
    private Date createdAt; // 创建时间
}