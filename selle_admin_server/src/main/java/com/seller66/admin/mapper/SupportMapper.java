package com.seller66.admin.mapper;

import com.seller66.admin.common.rest.BaseMapper;
import com.seller66.admin.entity.Support;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @description Support数据层
* @author  jcb
* @since  2025-05-25 13:05:56
*/
@Mapper
@Repository
public interface SupportMapper extends BaseMapper<Support> {

}
