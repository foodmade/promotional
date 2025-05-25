package com.seller66.admin.mapper;

import com.seller66.admin.entity.UmsAdminRoleRelation;

/**
 * 管理员角色关系表Mapper
 */
public interface UmsAdminRoleRelationMapper {
    /**
     * 根据ID删除
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 添加
     */
    int insert(UmsAdminRoleRelation record);

    /**
     * 根据ID查询
     */
    UmsAdminRoleRelation selectByPrimaryKey(Long id);

    /**
     * 更新
     */
    int updateByPrimaryKeySelective(UmsAdminRoleRelation record);

    /**
     * 通过管理员ID删除关系
     */
    int deleteByAdminId(Long adminId);
} 