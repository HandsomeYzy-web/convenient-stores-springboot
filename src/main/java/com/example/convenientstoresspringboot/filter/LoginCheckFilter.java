package com.example.convenientstoresspringboot.filter;

import com.example.convenientstoresspringboot.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

/**
 * 登录拦截过滤器
 */

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    // 路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("拦截请求：{}", request.getRequestURI());

        // 处理跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "token, Content-Type");

        // 如果是OPTIONS请求，直接放行
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String requestURI = request.getRequestURI();
        String[] urls = {"/users/login", "/users/logout","/images/**", "/upload", "/download"};
        if (check(urls, requestURI)) {
            log.info("本次请求{}不需要处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // 从请求头中获取token
        String token = request.getHeader("token");
        log.info("token: {}", token);

        if (token != null && !token.isEmpty()) {
            try {
                JwtUtils.parseToken(token); // 解析token
            } catch (Exception e) {
                log.info("解析token失败: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"code\": 401, \"message\": \"Token无效或已过期\"}");
                return;
            }
        } else {
            log.info("用户未登录");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"code\": 401, \"message\": \"用户未登录\"}");
            return;
        }

        // 放行请求
        filterChain.doFilter(request, response);
    }

    /**
     * 判断本次请求是否需要放行
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI); // 匹配成功返回true
            if (match) {
                return true;
            }
        }
        return false;
    }
}
