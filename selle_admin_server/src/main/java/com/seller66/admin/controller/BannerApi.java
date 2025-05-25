package com.seller66.admin.controller;

import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.utils.rest.BaseController;
import com.seller66.admin.biz.BannerBiz;
import com.seller66.admin.entity.Banner;
import com.seller66.admin.vo.BannerQueryVo;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
* @description Banner控制层
* @author  jcb
* @since  2025-05-25 11:10:34
*/
@RestController
@RequestMapping("/banner")
public class BannerApi extends BaseController<BannerBiz, Banner> {

    @GetMapping("/getBannerList")
    public CommonResultList<Banner> getBannerList(BannerQueryVo params) {
        return baseBiz.getBannerList(params);
    }

    @PostMapping("/createBanner")
    public CommonResult<Banner> createBanner(@RequestBody Banner banner) {
        if(banner.getImageUrl() == null){
            return CommonResult.failed("图片地址不能为空");
        }
        if(banner.getStatus() == null){
            return CommonResult.failed("状态不能为空");
        }
        banner.setCreateTime(new Date());
        baseBiz.createBanner(banner);
        return CommonResult.success();
    }

    @GetMapping("/deleteBanner")
    public CommonResult<String> deleteBanner(@RequestParam("bannerId") Integer bannerId) {
        if(bannerId == null){
            return CommonResult.failed("id不能为空");
        }
        baseBiz.deleteById(bannerId);
        return CommonResult.success();
    }

    @PostMapping("/updateBanner")
    public CommonResult<String> updateBanner(@RequestBody Banner banner) {
        if(banner.getId() == null){
            return CommonResult.failed("id不能为空");
        }
        baseBiz.updateSelectiveById(banner);
        return CommonResult.success();
    }
}
