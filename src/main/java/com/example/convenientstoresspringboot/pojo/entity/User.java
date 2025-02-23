package com.example.convenientstoresspringboot.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户实体类
 */
@Data
@TableName("users")
public class User {
    private String userId;
    private String username;
    private String password;
    private String role;
    private String email;
    private String phone;
    private Integer status;
    private String createdAt;
    private String updatedAt;
    private String avatar;
}
