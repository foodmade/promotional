package com.seller66.admin.controller;

import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.utils.rest.BaseController;
import com.seller66.admin.biz.SellerToolsBiz;
import com.seller66.admin.entity.SellerTools;
import com.seller66.admin.vo.SellerToolsQuery;
import org.springframework.web.bind.annotation.*;

/**
* @description SellerTools控制层
* @author  jcb
* @since  2025-05-23 14:16:36
*/
@RestController
@RequestMapping("/sellerTools")
public class SellerToolsApi extends BaseController<SellerToolsBiz, SellerTools> {

    // 示例：获取所有 SellerTools
     @GetMapping("/getAll")
     public CommonResultList<SellerTools> getAll(SellerToolsQuery sellerToolsQuery) {
         return baseBiz.getAll(sellerToolsQuery);
     }

    // 示例：创建新的 SellerTools
     @PostMapping("/create")
     public CommonResult<SellerTools> create(@RequestBody SellerTools sellerTools) {
         return baseBiz.create(sellerTools);
     }

     @GetMapping("/getDetail")
    public CommonResult<SellerTools> getDetail(@RequestParam Long id) {
        SellerTools sellerTools = baseBiz.selectById(id);
        if (sellerTools == null) {
            return CommonResult.failed("SellerTools not found");
        }
        return CommonResult.success(sellerTools);
    }

    @PostMapping("/update")
    public CommonResult<Boolean> update(@RequestBody SellerTools sellerTools) {
        if (sellerTools == null || sellerTools.getId() == null) {
            return CommonResult.failed("SellerTools ID cannot be null");
        }
        baseBiz.updateSelectiveById(sellerTools);
        return CommonResult.success(true);
    }

    @GetMapping("/delete")
    public CommonResult<Boolean> delete(@RequestParam Long id) {
        if (id == null) {
            return CommonResult.failed("SellerTools ID cannot be null");
        }
        baseBiz.deleteById(id);
        return CommonResult.success(true);
    }

}
