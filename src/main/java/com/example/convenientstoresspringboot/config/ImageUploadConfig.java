package com.example.convenientstoresspringboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置图片上传路径, 解决需要重启才能拿到资源的问题
 */
@Configuration
public class ImageUploadConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 设置访问路径映射
        registry.addResourceHandler("/images/**")
                // 设置文件存储路径
                .addResourceLocations("file:D:/WebProject/convenient-stores-springboot/src/main/resources/static/images");
    }
}
