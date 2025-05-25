package com.seller66.admin.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UmsAdminRegisterParam {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
    @Email(message = "邮箱格式不合法")
    private String email;
    private String nickName;
    private String note;
} 