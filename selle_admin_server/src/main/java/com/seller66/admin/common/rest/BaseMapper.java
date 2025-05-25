package com.seller66.admin.common.rest;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 自定义通用 Mapper 接口，统一封装基础方法
 * 
 * @param <T> 实体类类型
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>, InsertListMapper<T> {
    // 这里不要添加任何方法，避免和通用 Mapper 冲突
}