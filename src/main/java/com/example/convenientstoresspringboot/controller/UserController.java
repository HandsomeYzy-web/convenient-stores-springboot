package com.example.convenientstoresspringboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.convenientstoresspringboot.common.Result;
import com.example.convenientstoresspringboot.pojo.entity.User;
import com.example.convenientstoresspringboot.pojo.entity.UserLoginLog;
import com.example.convenientstoresspringboot.service.UserLoginLogService;
import com.example.convenientstoresspringboot.service.UserService;
import com.example.convenientstoresspringboot.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 用户表控制器
 */

@Slf4j
@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserLoginLogService userLoginLogService;

    /**
     * 登录
     * @param request
     * @param user
     */
    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody User user) {
        String userId = user.getUserId();
        String password = user.getPassword();
        String role = userService.determineRole(userId);
        log.info("用户登录 用户名：{}", userId);

        User getUser = userService.getUserByUserIdAndRole(userId, role);
        boolean loginSuccess = getUser != null && userService.isPasswordValid(getUser, password);

        if (loginSuccess && getUser.getStatus() != 0) {
            UserLoginLog userLoginLog = userLoginLogService.recordLoginLog(request, userId, "登录成功");
            userLoginLogService.save(userLoginLog);
            Map<String, Object> claims = new HashMap<>();
            claims.put("user_id", userId);
//            claims.put("username", getUser.getUsername());
//            claims.put("role", role);
            String token = JwtUtils.generateToken(claims);
            return Result.success(token);
        } else {
            String loginStatus = loginSuccess ? "用户已被禁用" : "用户名或密码错误";
            UserLoginLog userLoginLog = userLoginLogService.recordLoginLog(request, userId, loginStatus);
            userLoginLogService.save(userLoginLog);
            return Result.error(loginStatus);
        }
    }

    /**
     * 新增用户
     * @param user
     */
    @PostMapping
    public Result save(@RequestBody User user) {
        log.info("新增用户：{}", user);
        if (user.getPassword() == null) {
            // 默认密码123456
            user.setPassword("123456");
        }
        user.setCreatedAt(null);
        user.setUpdatedAt(null);
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        if (!user.getRole().equals("member")) {
            user.setUserId(userService.determineUserId(user.getRole()));
        }
        userService.save(user);
        return Result.success("新增成功");
    }

    /**
     * 通过token 获取登录用户信息
     */
    @GetMapping("/info")
    public Result getUserInfo(HttpServletRequest request) {
        // 从请求头中获取token
        String token = request.getHeader("token");
        log.info("token: {}", token);
        if (token == null || token.isEmpty()) {
            return Result.error("Token不能为空");
        }

        // 解析token，获取用户ID
        String userId = null;
        try {
            Claims claims = JwtUtils.parseToken(token); // 解析token
            userId = claims.get("user_id", String.class); // 从token中获取用户ID
        } catch (Exception e) {
            log.error("解析token失败", e);
            return Result.error("Token无效或已过期");
        }

        // 根据用户ID查询用户信息
        String role = userService.determineRole(userId);
        User getUser = userService.getUserByUserIdAndRole(userId, role);

        // 返回用户信息
        return Result.success(getUser);
    }

    /**
     * 获取用户信息
     * @param
     *
     */
    @GetMapping("/infos")
    public Result getUserInfoByRole(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int limit,
                                    @RequestParam(defaultValue = "null") String role) {
        Page<User> users = userService.getSuppliers(page, limit, role);
        return Result.success(users);
    }

    /**
     * 修改用户信息
     * @param user
     */
    // 通过userid修改
    @PutMapping
    public Result update(@RequestBody User user) {
        log.info("修改用户信息：{}", user);
        // 调用 service 更新用户
        boolean updated = userService.updateUserById(user);
        if (updated) {
            return Result.success();
        } else {
            return Result.error("更新失败");
        }
    }
    // export const reqDeleteTrademark = (userid: string) => Request.delete<any, any>(API.DELETE_TRADEMARK_URL+`/${userid}`);
    @DeleteMapping("/{userid}")
    public Result delete(@PathVariable String userid) {
        log.info("删除用户：{}", userid);
        boolean deleted = userService.removeByUserId(userid);
        if (deleted) {
            return Result.success();
        } else {
            return Result.error("删除失败");
        }
    }
}
