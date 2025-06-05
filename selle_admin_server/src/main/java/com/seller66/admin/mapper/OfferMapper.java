package com.seller66.admin.mapper;

import com.seller66.admin.common.rest.BaseMapper;
import com.seller66.admin.dto.OfferDTO;
import com.seller66.admin.entity.Offer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @description Offer数据层
* @author  jcb
* @since  2025-05-26 17:17:50
*/
@Mapper
@Repository
public interface OfferMapper extends BaseMapper<Offer> {

    List<OfferDTO> getOfferLists();
}
