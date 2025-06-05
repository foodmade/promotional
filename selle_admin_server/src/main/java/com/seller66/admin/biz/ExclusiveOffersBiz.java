package com.seller66.admin.biz;

import com.seller66.admin.dto.ExclusiveOffersDTO;
import com.seller66.admin.mapper.ExclusiveOffersMapper;
import com.seller66.admin.common.model.BaseBiz;
import com.seller66.admin.entity.ExclusiveOffers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @description ExclusiveOffers业务层
* @author  jcb
* @since  2025-05-26 17:54:23
*/
@Slf4j
@Service
@Transactional
public class ExclusiveOffersBiz extends BaseBiz<ExclusiveOffersMapper, ExclusiveOffers> {

    public List<ExclusiveOffersDTO> getExclusiveOffersList() {
        return this.mapper.getExclusiveOffersList();
    }
}
