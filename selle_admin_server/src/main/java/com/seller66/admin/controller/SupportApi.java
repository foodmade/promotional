package com.seller66.admin.controller;

import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.utils.rest.BaseController;
import com.seller66.admin.biz.SupportBiz;
import com.seller66.admin.entity.Support;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
* @description Support控制层
* @author  jcb
* @since  2025-05-25 13:05:56
*/
@RestController
@RequestMapping("/support")
public class SupportApi extends BaseController<SupportBiz, Support> {

    @GetMapping("/list")
    public CommonResultList<Support> list() {
        return CommonResultList.success(baseBiz.selectListAll());
    }

    @PostMapping("/create")
    public CommonResult<Boolean> create(@RequestBody Support support) {
        support.setCreateTime(new Date());
        baseBiz.insertSelective(support);
        return CommonResult.success();
    }

    @PostMapping("/update")
    public CommonResult<Boolean> update(@RequestBody Support support) {
        if(support.getId() == null){
            return CommonResult.failed("id不能为空");
        }
        baseBiz.updateSelectiveById(support);
        return CommonResult.success();
    }

    @GetMapping("/delete")
    public CommonResult<Boolean> delete(@RequestParam("id") Integer id) {
        baseBiz.deleteById(id);
        return CommonResult.success();
    }
}
