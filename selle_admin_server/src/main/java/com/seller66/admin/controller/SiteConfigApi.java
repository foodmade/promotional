package com.seller66.admin.controller;

import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.common.model.PageModel;
import com.seller66.admin.dto.SiteBaseInfoDTO;
import com.seller66.admin.dto.SiteConfigDTO;
import com.seller66.admin.utils.ConvertUtil;
import com.seller66.admin.utils.rest.BaseController;
import com.seller66.admin.biz.SiteConfigBiz;
import com.seller66.admin.entity.SiteConfig;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @description SiteConfig控制层
* @author  jcb
* @since  2025-06-05 17:11:23
*/
@RestController
@RequestMapping("/siteConfig")
public class SiteConfigApi extends BaseController<SiteConfigBiz, SiteConfig> {

    @PostMapping("/updateConfig")
    public CommonResult<Boolean> updateConfig(@RequestBody SiteConfig siteConfig) {
        baseBiz.updateConfig(siteConfig);
        return CommonResult.success(true);
    }

    @GetMapping("/getSiteConfig")
    public CommonResult<SiteConfig> getSiteConfig() {
        SiteConfig config = baseBiz.selectConfig();
        return CommonResult.success(config);
    }

}
