package com.seller66.admin.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.common.model.TableRestResponse;
import com.seller66.admin.dto.OfferDTO;
import com.seller66.admin.mapper.OfferMapper;
import com.seller66.admin.common.model.BaseBiz;
import com.seller66.admin.entity.Offer;
import com.seller66.admin.vo.OfferSearchVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @description Offer业务层
* @author  jcb
* @since  2025-05-26 17:17:50
*/
@Slf4j
@Service
@Transactional
public class OfferBiz extends BaseBiz<OfferMapper, Offer> {

    public CommonResultList<OfferDTO> getOfferList(OfferSearchVo offerSearchVo) {
        PageHelper.startPage(offerSearchVo.getPageNum(), offerSearchVo.getPageSize());
        List<OfferDTO> list = this.mapper.getOfferList(offerSearchVo);
        PageInfo<OfferDTO> pageInfo = new PageInfo<>(list);
        return CommonResultList.success(list, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }
}
