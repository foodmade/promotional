package com.seller66.admin.controller;

import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.utils.rest.BaseController;
import com.seller66.admin.biz.LangBiz;
import com.seller66.admin.entity.Lang;
import com.seller66.admin.vo.LangModelVo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
* @description Lang控制层
* @author  jcb
* @since  2025-06-05 19:48:24
*/
@RestController
@RequestMapping("/lang")
public class LangApi extends BaseController<LangBiz, Lang> {

    @GetMapping("/getAllLangs")
    public CommonResult<HashMap<String,Object>> getAllLangs() {
        return CommonResult.success(baseBiz.getAllLangsMap());
    }

    @PostMapping("/updateLang")
    public CommonResult<Boolean> updateLang(@RequestBody LangModelVo langModelVo) {
        baseBiz.updateLang(langModelVo);
        return CommonResult.success(true);
    }
}
