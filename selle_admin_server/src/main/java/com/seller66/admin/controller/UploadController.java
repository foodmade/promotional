package com.seller66.admin.controller;

import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.dto.UploadDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {
    
    @Value("${upload.path}")
    private String uploadPath;
    
    @Value("${upload.url-prefix}")
    private String urlPrefix;
    
    @PostMapping("/image")
    public CommonResult<UploadDTO> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return CommonResult.failed("请选择一个图片上传");
        }

        // 检查文件类型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return CommonResult.failed("只能上传图片文件");
        }

        try {
            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
            String newFilename = UUID.randomUUID() + extension;

            // 创建上传目录
            File dir = new File(uploadPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 保存文件
            File destFile = new File(dir, newFilename);
            file.transferTo(destFile);

            // 返回文件访问路径
            String fileUrl = "/images/" + newFilename;
            return CommonResult.success(new UploadDTO(fileUrl));
        } catch (IOException e) {
            log.error("文件上传失败：{}" ,e.getMessage());
            return CommonResult.failed(e.getMessage());
        }
    }
}
