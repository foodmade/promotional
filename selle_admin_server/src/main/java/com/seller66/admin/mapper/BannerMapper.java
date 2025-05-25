package com.seller66.admin.mapper;

import com.seller66.admin.common.rest.BaseMapper;
import com.seller66.admin.entity.Banner;
import com.seller66.admin.vo.BannerQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @description Banner数据层
* @author  jcb
* @since  2025-05-25 11:10:34
*/
@Mapper
@Repository
public interface BannerMapper extends BaseMapper<Banner> {

    List<Banner> getAllBannerList(@Param("bannerQueryVo") BannerQueryVo bannerQueryVo);
}
