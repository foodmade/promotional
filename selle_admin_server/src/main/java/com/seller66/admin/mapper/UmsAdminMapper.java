package com.seller66.admin.mapper;

import com.seller66.admin.entity.UmsAdmin;
import org.apache.ibatis.annotations.Param;

/**
 * 管理员表Mapper
 */
public interface UmsAdminMapper {
    /**
     * 根据ID删除
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 添加
     */
    int insert(UmsAdmin record);

    /**
     * 根据ID查询
     */
    UmsAdmin selectByPrimaryKey(Long id);

    /**
     * 更新
     */
    int updateByPrimaryKeySelective(UmsAdmin record);

    /**
     * 根据用户名查询管理员
     */
    UmsAdmin selectByUsername(String username);
} 