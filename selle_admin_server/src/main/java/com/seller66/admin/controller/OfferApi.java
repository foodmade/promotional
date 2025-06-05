package com.seller66.admin.controller;

import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.common.model.TableRestResponse;
import com.seller66.admin.dto.OfferDTO;
import com.seller66.admin.utils.rest.BaseController;
import com.seller66.admin.biz.OfferBiz;
import com.seller66.admin.entity.Offer;
import com.seller66.admin.vo.OfferSearchVo;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
* @description Offer控制层
* @author  jcb
* @since  2025-05-26 17:17:50
*/
@RestController
@RequestMapping("/promotionPopup")
public class OfferApi extends BaseController<OfferBiz, Offer> {

    @GetMapping("/getList")
    public CommonResultList<OfferDTO> getList() {
        return baseBiz.getOfferList();
    }

    @PostMapping("/update")
    public CommonResult<Boolean> updateOffer(@RequestBody Offer offer) {
        if(offer.getId() == null){
            return CommonResult.failed("id不能为空");
        }
        baseBiz.updateSelectiveById(offer);
        return CommonResult.success(true);
    }

    @PostMapping("/create")
    public CommonResult<Boolean> createOffer(@RequestBody Offer offer) {
        offer.setCreateTime(new Date());
        baseBiz.insertSelective(offer);
        return CommonResult.success(true);
    }

    @GetMapping("/delete")
    public CommonResult<Boolean> deleteOffer(Long id) {
        if(id == null){
            return CommonResult.failed("id不能为空");
        }
        baseBiz.deleteById(id);
        return CommonResult.success(true);
    }
}
