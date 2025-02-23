package com.example.convenientstoresspringboot.controller;

import com.example.convenientstoresspringboot.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
@Slf4j
@RestController
public class UploadController {
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        log.info("接受参数：{}", file);
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 生成新的文件名
        String newFileName = UUID.randomUUID().toString() + extension;
        // 文件存储路径
        String filePath = "D:/WebProject/convenient-stores-springboot/src/main/resources/static/images/" + newFileName;
        // 保存文件
        file.transferTo(new File(filePath));

        // 返回文件的访问 URL
        String fileUrl = "http://localhost:8080/images/" + newFileName;
        return Result.success(fileUrl);
    }
    // 文件下载
    @GetMapping("/download")
    public Result download(@RequestParam("fileName") String fileName) {
        log.info("接受参数：{}", fileName);

        // 防止路径遍历攻击
        String safeFileName = fileName.replace("..", "").replace("/", "");

        // 文件存储路径
        String filePath = "D:/WebProject/convenient-stores-springboot/src/main/resources/static/images/" + safeFileName;
        File file = new File(filePath);

        // 检查文件是否存在
        if (!file.exists()) {
            return Result.error("文件不存在");
        }

        // 返回文件路径（实际项目中可以直接返回文件流）
        return Result.success(filePath);
    }
}
