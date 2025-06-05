package com.seller66.admin.mapper;

import com.seller66.admin.common.rest.BaseMapper;
import com.seller66.admin.entity.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @description Lang数据层
* @author  jcb
* @since  2025-06-05 19:48:24
*/
@Mapper
@Repository
public interface LangMapper extends BaseMapper<Lang> {

}
