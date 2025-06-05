package com.seller66.admin.mapper;

import com.seller66.admin.common.rest.BaseMapper;
import com.seller66.admin.entity.SiteConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @description SiteConfig数据层
* @author  jcb
* @since  2025-06-05 18:07:43
*/
@Mapper
@Repository
public interface SiteConfigMapper extends BaseMapper<SiteConfig> {

    SiteConfig selectLastOne();

}
