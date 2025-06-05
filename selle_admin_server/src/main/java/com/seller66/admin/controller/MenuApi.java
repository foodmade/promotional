package com.seller66.admin.controller;

import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.dto.MenuDTO;
import com.seller66.admin.utils.rest.BaseController;
import com.seller66.admin.biz.MenuBiz;
import com.seller66.admin.entity.Menu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @description Menu控制层
* @author  jcb
* @since  2025-06-05 19:37:19
*/
@RestController
@RequestMapping("/menu")
public class MenuApi extends BaseController<MenuBiz, Menu> {

    @GetMapping("/tree")
    public CommonResultList<MenuDTO> tree() {
        return CommonResultList.success(baseBiz.getMenuTree());
    }

}
