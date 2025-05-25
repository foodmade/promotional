package com.seller66.admin.controller;

import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.utils.rest.BaseController;
import com.seller66.admin.biz.BrandsBiz;
import com.seller66.admin.entity.Brands;
import org.springframework.web.bind.annotation.*;

/**
* @description Brands控制层
* @author  jcb
* @since  2025-05-25 12:16:52
*/
@RestController
@RequestMapping("/brands")
public class BrandsApi extends BaseController<BrandsBiz, Brands> {


    @GetMapping("/getAllBrands")
    public CommonResultList<Brands> getAllBrands() {
        return baseBiz.getAllBrands();
    }

    @GetMapping("/getBrandsDetail")
    public CommonResult<Brands> getBrandsDetail(Long brandId) {
        if(brandId == null){
            return CommonResult.failed("id不能为空");
        }
        return CommonResult.success(baseBiz.selectById(brandId));
    }

    @PostMapping("/updateBrands")
    public CommonResult<String> updateBrands(@RequestBody Brands brands) {
        if(brands.getId() == null){
            return CommonResult.failed("id不能为空");
        }
        baseBiz.updateSelectiveById(brands);
        return CommonResult.success();
    }

    @PostMapping("/createBrands")
    public CommonResult<Boolean> createBrands(@RequestBody Brands brands) {
        if(brands.getLogo() == null){
            return CommonResult.failed("品牌logo不能为空");
        }
        if(brands.getRating() == null){
            return CommonResult.failed("评级不能为空");
        }
        if(brands.getSlug() == null){
            return CommonResult.failed("跳转slug不能为空");
        }
        baseBiz.insertSelective(brands);
        return CommonResult.success(true);
    }

    @GetMapping("/deleteBrands")
    public CommonResult<String> deleteBrands(Long brandId) {
        if(brandId == null){
            return CommonResult.failed("id不能为空");
        }
        baseBiz.deleteById(brandId);
        return CommonResult.success();
    }
}
