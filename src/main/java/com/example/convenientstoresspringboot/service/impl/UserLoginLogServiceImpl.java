package com.example.convenientstoresspringboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.convenientstoresspringboot.mapper.UserLoginLogMapper;
import com.example.convenientstoresspringboot.pojo.entity.UserLoginLog;
import com.example.convenientstoresspringboot.service.UserLoginLogService;
import com.example.convenientstoresspringboot.utils.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements UserLoginLogService {
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;
    @Override
    public UserLoginLog recordLoginLog(HttpServletRequest request, String userId, String loginStatus) {
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(userId);
        userLoginLog.setLoginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        userLoginLog.setLoginStatus(loginStatus);
        userLoginLog.setLoginIp(IpUtils.getClientIpAddress(request)); // 使用工具类获取IP地址
        return userLoginLog;
    }
}
