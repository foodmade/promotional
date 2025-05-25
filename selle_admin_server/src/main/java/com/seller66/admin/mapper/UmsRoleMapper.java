package com.seller66.admin.mapper;

import com.seller66.admin.entity.UmsRole;

import java.util.List;

/**
 * 角色表Mapper
 */
public interface UmsRoleMapper {
    /**
     * 根据ID删除
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 添加
     */
    int insert(UmsRole record);

    /**
     * 根据ID查询
     */
    UmsRole selectByPrimaryKey(Long id);

    /**
     * 更新
     */
    int updateByPrimaryKeySelective(UmsRole record);

    /**
     * 获取用户所有角色
     */
    List<UmsRole> getRoleList(Long adminId);
} 