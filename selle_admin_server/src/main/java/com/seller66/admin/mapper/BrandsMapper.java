package com.seller66.admin.mapper;

import com.seller66.admin.common.rest.BaseMapper;
import com.seller66.admin.entity.Brands;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @description Brands数据层
* @author  jcb
* @since  2025-05-25 12:16:52
*/
@Mapper
@Repository
public interface BrandsMapper extends BaseMapper<Brands> {

    List<Brands> getAllBransList();
}
