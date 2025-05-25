package com.seller66.admin.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 管理员角色关系表
 */
@Data
public class UmsAdminRoleRelation implements Serializable {
    private Long id;

    /**
     * 管理员ID
     */
    private Long adminId;

    /**
     * 角色ID
     */
    private Long roleId;

    private static final long serialVersionUID = 1L;
} 