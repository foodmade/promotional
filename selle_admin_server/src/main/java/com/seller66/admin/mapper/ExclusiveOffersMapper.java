package com.seller66.admin.mapper;

import com.seller66.admin.common.rest.BaseMapper;
import com.seller66.admin.dto.ExclusiveOffersDTO;
import com.seller66.admin.entity.ExclusiveOffers;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @description ExclusiveOffers数据层
* @author  jcb
* @since  2025-05-26 17:54:23
*/
@Mapper
@Repository
public interface ExclusiveOffersMapper extends BaseMapper<ExclusiveOffers> {

    List<ExclusiveOffersDTO> getExclusiveOffersList();
}
