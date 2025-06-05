package com.seller66.admin.biz;

import com.seller66.admin.dto.MenuDTO;
import com.seller66.admin.mapper.MenuMapper;
import com.seller66.admin.common.model.BaseBiz;
import com.seller66.admin.entity.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.seller66.admin.utils.ConvertUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
* @description Menu业务层
* @author  jcb
* @since  2025-06-05 19:37:19
*/
@Slf4j
@Service
@Transactional
public class MenuBiz extends BaseBiz<MenuMapper, Menu> {

    public List<MenuDTO> getMenuTree() {
        List<Menu> menuList = selectListAll();
        if (menuList == null || menuList.isEmpty()) {
            return new ArrayList<>();
        }

        // 1. 转为DTO并建立id映射
        HashMap<Integer, MenuDTO> dtoMap = new HashMap<>();
        for (Menu menu : menuList) {
            MenuDTO dto = ConvertUtil.convert(menu, MenuDTO.class);
            dto.setChildren(new ArrayList<>());
            dtoMap.put(dto.getId(), dto);
        }

        // 2. 组装树结构
        List<MenuDTO> rootList = new ArrayList<>();
        for (MenuDTO dto : dtoMap.values()) {
            if (dto.getParentId() == null || dto.getParentId() == 0) {
                rootList.add(dto);
            } else {
                MenuDTO parent = dtoMap.get(dto.getParentId());
                if (parent != null) {
                    parent.getChildren().add(dto);
                }
            }
        }
        return rootList;
    }
}
