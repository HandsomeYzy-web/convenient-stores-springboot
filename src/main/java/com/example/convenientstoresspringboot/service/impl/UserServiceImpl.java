package com.example.convenientstoresspringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.convenientstoresspringboot.mapper.UserMapper;
import com.example.convenientstoresspringboot.pojo.entity.User;
import com.example.convenientstoresspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUserIdAndRole(String userId, String role) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserId, userId).eq(User::getRole, role);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean isPasswordValid(User user, String password) {
        if (user == null) {
            return false;
        }
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        return encryptedPassword.equals(user.getPassword());
    }

    @Override
    public String determineRole(String userId) {
        String role;
        if (userId.length() == 11) {
            role = "member";
        } else {
            role = userId.substring(0, 1);
            role = switch (role) {
                case "1" -> "admin";
                case "2" -> "manager";
                case "3" -> "staff";
                case "4" -> "supplier";
                default -> "member";
            };
        }
        return role;
    }

    public Page<User> getUsers(int page, int limit, String role) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!role.equals("all")) {
            queryWrapper.eq("role", role);
        }
        // 分页查询
        Page<User> pageResult = new Page<>(page, limit);
        return userMapper.selectPage(pageResult, queryWrapper);
    }

    @Override
    public String determineUserId(String role) {
        // 变为整数加一再转换为字符串
        String userId = userMapper.findMaxUserIdByRole(role);
        userId = String.valueOf(Integer.parseInt(userId) + 1);
        return userId;
    }

    @Override
    public boolean updateUserById(User user) {
        // 创建更新条件
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", user.getUserId()); // 通过 userid 查找用户
        return userMapper.update(user, updateWrapper) > 0;
    }

    @Override
    public boolean removeByUserId(String userid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userid);
        return userMapper.delete(queryWrapper) > 0;
    }

    @Override
    public User getUserByUserId(String userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return userMapper.selectOne(queryWrapper);
    }
}
