package com.seller66.admin.controller;

import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.dto.ExclusiveOffersDTO;
import com.seller66.admin.utils.rest.BaseController;
import com.seller66.admin.biz.ExclusiveOffersBiz;
import com.seller66.admin.entity.ExclusiveOffers;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
* @description ExclusiveOffers控制层
* @author  jcb
* @since  2025-05-26 17:54:23
*/
@RestController
@RequestMapping("/exclusiveOffers")
public class ExclusiveOffersApi extends BaseController<ExclusiveOffersBiz, ExclusiveOffers> {

    @GetMapping("/getList")
    public CommonResultList<ExclusiveOffersDTO> getList() {
        return CommonResultList.success(baseBiz.getExclusiveOffersList());
    }

    @PostMapping("/create")
    public CommonResult<Boolean> create(@RequestBody ExclusiveOffers exclusiveOffers) {
        exclusiveOffers.setCreateTime(new Date());
        baseBiz.insertSelective(exclusiveOffers);
        return CommonResult.success();
    }

    @PostMapping("/update")
    public CommonResult<Boolean> update(@RequestBody ExclusiveOffers exclusiveOffers) {
        if(exclusiveOffers.getId() == null) {
            return CommonResult.failed("ID不能为空");
        }
        baseBiz.updateSelectiveById(exclusiveOffers);
        return CommonResult.success();
    }

    @GetMapping("/delete")
    public CommonResult<Boolean> delete(Long id) {
        if(id == null) {
            return CommonResult.failed("ID不能为空");
        }
        baseBiz.deleteById(id);
        return CommonResult.success();
    }
}
