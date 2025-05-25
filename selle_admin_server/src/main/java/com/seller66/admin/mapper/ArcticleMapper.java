package com.seller66.admin.mapper;

import com.seller66.admin.common.rest.BaseMapper;
import com.seller66.admin.entity.Arcticle;
import com.seller66.admin.vo.ArticleQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @description Arcticle数据层
* @author  jcb
* @since  2025-05-22 13:05:45
*/
@Mapper
@Repository
public interface ArcticleMapper extends BaseMapper<Arcticle> {

    List<Arcticle> selectAllByFilter(@Param("queryVo") ArticleQueryVo queryVo);

    Arcticle selectBySlug(@Param("slug") String slug);
}
