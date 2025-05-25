package com.seller66.admin.service.impl;

import com.seller66.admin.dto.UmsAdminLoginParam;
import com.seller66.admin.dto.UmsAdminRegisterParam;
import com.seller66.admin.entity.UmsAdmin;
import com.seller66.admin.entity.UmsRole;
import com.seller66.admin.mapper.UmsAdminMapper;
import com.seller66.admin.mapper.UmsRoleMapper;
import com.seller66.admin.security.AdminUserDetails;
import com.seller66.admin.service.UmsAdminService;
import com.seller66.admin.utils.JwtTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 管理员服务实现类
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService, UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    
    @Autowired
    private UmsAdminMapper adminMapper;
    
    @Autowired
    private UmsRoleMapper roleMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        return adminMapper.selectByUsername(username);
    }

    @Override
    public String login(UmsAdminLoginParam loginParam) {
        UserDetails userDetails = loadUserByUsername(loginParam.getUsername());
        if (!passwordEncoder.matches(loginParam.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        return jwtTokenUtils.generateToken(userDetails);
    }

    @Override
    public UmsAdmin register(UmsAdminRegisterParam registerParam) {
        // 查询是否有相同用户名的用户
        UmsAdmin existAdmin = adminMapper.selectByUsername(registerParam.getUsername());
        if (existAdmin != null) {
            throw new RuntimeException("用户名已存在");
        }
        // 创建用户
        UmsAdmin admin = new UmsAdmin();
        BeanUtils.copyProperties(registerParam, admin);
        admin.setCreateTime(new Date());
        admin.setStatus(1);
        // 将密码进行加密操作
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminMapper.insert(admin);
        return admin;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UmsAdmin admin = adminMapper.selectByUsername(username);
        if (admin != null) {
            return admin;
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
} 