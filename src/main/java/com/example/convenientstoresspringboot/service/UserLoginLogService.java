package com.example.convenientstoresspringboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.convenientstoresspringboot.pojo.entity.UserLoginLog;
import jakarta.servlet.http.HttpServletRequest;

public interface UserLoginLogService extends IService<UserLoginLog> {
    UserLoginLog recordLoginLog(HttpServletRequest request, String userId, String loginStatus);
}
