package com.example.convenientstoresspringboot.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("replenishments")
public class Replenishment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String productId;
    private Integer quantity;
    private String requesterId;
    private String approverId;
    private String supplierId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
