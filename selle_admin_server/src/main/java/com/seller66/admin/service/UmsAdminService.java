package com.seller66.admin.service;

import com.seller66.admin.dto.UmsAdminLoginParam;
import com.seller66.admin.dto.UmsAdminRegisterParam;
import com.seller66.admin.entity.UmsAdmin;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 管理员服务接口
 */
public interface UmsAdminService extends UserDetailsService {
    /**
     * 根据用户名获取管理员
     */
    UmsAdmin getAdminByUsername(String username);
    
    /**
     * 用户登录
     */
    String login(UmsAdminLoginParam loginParam);
    
    /**
     * 用户注册
     */
    UmsAdmin register(UmsAdminRegisterParam registerParam);
    
    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);
} 