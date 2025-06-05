package com.seller66.admin.mapper;

import com.seller66.admin.common.rest.BaseMapper;
import com.seller66.admin.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @description Menu数据层
* @author  jcb
* @since  2025-06-05 19:37:19
*/
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

}
