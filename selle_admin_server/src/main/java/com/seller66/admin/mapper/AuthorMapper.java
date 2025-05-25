package com.seller66.admin.mapper;

import com.seller66.admin.common.rest.BaseMapper;
import com.seller66.admin.entity.Author;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @description Author数据层
* @author  jcb
* @since  2025-04-20 22:15:34
*/
@Mapper
@Repository
public interface AuthorMapper extends BaseMapper<Author> {

    Author selectByName(@Param("name") String name);
}
