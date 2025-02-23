package com.example.convenientstoresspringboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.convenientstoresspringboot.pojo.entity.User;

public interface UserService extends IService<User> {
    User getUserByUserIdAndRole(String userId, String role);
    boolean isPasswordValid(User user, String password);
    String determineRole(String userId);
    Page<User> getSuppliers(int page, int limit, String role);
    String determineUserId(String role);
    boolean updateUserById(User user);
    boolean removeByUserId(String userid);
}
