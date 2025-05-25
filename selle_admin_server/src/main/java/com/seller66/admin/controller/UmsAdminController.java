package com.seller66.admin.controller;

import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.dto.UmsAdminLoginParam;
import com.seller66.admin.dto.UmsAdminRegisterParam;
import com.seller66.admin.entity.UmsAdmin;
import com.seller66.admin.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class UmsAdminController {
    @Autowired
    private UmsAdminService adminService;

    @PostMapping("/login")
    public CommonResult<String> login(@Validated @RequestBody UmsAdminLoginParam loginParam) {
        String token = adminService.login(loginParam);
        return CommonResult.success(token);
    }

    @PostMapping("/register")
    public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsAdminRegisterParam registerParam) {
        UmsAdmin admin = adminService.register(registerParam);
        return CommonResult.success(admin);
    }
} 