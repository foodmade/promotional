package com.seller66.admin.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 */
@Data
public class UmsRole implements Serializable {
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 启用状态：0->禁用；1->启用
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
} 