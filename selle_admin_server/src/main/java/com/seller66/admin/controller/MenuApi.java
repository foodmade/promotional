package com.seller66.admin.controller;

import com.seller66.admin.common.api.CommonResult;
import com.seller66.admin.common.api.CommonResultList;
import com.seller66.admin.dto.MenuDTO;
import com.seller66.admin.utils.rest.BaseController;
import com.seller66.admin.biz.MenuBiz;
import com.seller66.admin.entity.Menu;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/disableMenu")
    public CommonResult<Menu> disableMenu(Long id) {
        if (id == null) {
            return CommonResult.failed("id不能为空");
        }
        Menu menu = baseBiz.selectById(id);
        if(menu == null){
            return CommonResult.failed("菜单不存在");
        }
        if(menu.getVisible()){
            //需要禁用
            baseBiz.updateMenuStatus(id,false);
        }else {
            baseBiz.updateMenuStatus(id,true);
        }
        menu = baseBiz.selectById(id);
        return CommonResult.success(menu);
    }

    @PostMapping("/updateMenu")
    public CommonResult<Boolean> updateMenu(@RequestBody MenuDTO menu) {
        if (menu.getId() == null) {
            return CommonResult.failed("id不能为空");
        }
        baseBiz.updateEnumName(menu);
        return CommonResult.success(true);
    }
}
