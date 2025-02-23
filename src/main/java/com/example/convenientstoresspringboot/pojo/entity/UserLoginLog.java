package com.example.convenientstoresspringboot.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 用户登录日志实体类
 */
@Data
public class UserLoginLog {
    private String userId;
    private String loginTime;
    private String loginStatus;
    private String loginIp;
}
