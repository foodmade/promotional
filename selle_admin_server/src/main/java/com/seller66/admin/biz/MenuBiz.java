package com.seller66.admin.biz;

import com.seller66.admin.common.enmu.LangTypeEnum;
import com.seller66.admin.dto.MenuDTO;
import com.seller66.admin.mapper.MenuMapper;
import com.seller66.admin.common.model.BaseBiz;
import com.seller66.admin.entity.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LangBiz langBiz;

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
            dto.setMenuUsName(langBiz.langFindByKey(dto.getName(), LangTypeEnum.EN_US));
            dto.setMenuCnName(langBiz.langFindByKey(dto.getName(), LangTypeEnum.ZH_CN));
            String titleResourceKey = buildTitleResourceKey(dto.getName());
            String subTitleResourceKey = buildSubTitleResourceKey(dto.getName());
            dto.setMenuTitleCnDesc(langBiz.langFindByKey(titleResourceKey, LangTypeEnum.ZH_CN));
            dto.setMenuTitleUsDesc(langBiz.langFindByKey(titleResourceKey, LangTypeEnum.EN_US));
            dto.setMenuSubTitleCnDesc(langBiz.langFindByKey(subTitleResourceKey, LangTypeEnum.ZH_CN));
            dto.setMenuSubTitleUsDesc(langBiz.langFindByKey(subTitleResourceKey, LangTypeEnum.EN_US));
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

    public void disableMenu(Long id) {
        this.mapper.disableMenu(id);
    }

    public void updateMenuStatus(Long id, Boolean status) {
        this.mapper.updateMenuStatus(id, status);
    }

    public void updateEnumName(MenuDTO menu) {
        if(menu.getId() == null){
            return;
        }
        Menu existingMenu = this.mapper.selectByPrimaryKey(menu.getId());
        if(menu.getId() == null){
            return;
        }
        langBiz.setLangValue(existingMenu.getName(),menu.getMenuCnName(),LangTypeEnum.ZH_CN);
        langBiz.setLangValue(existingMenu.getName(),menu.getMenuUsName(),LangTypeEnum.EN_US);
        String titleResourceKey = buildTitleResourceKey(existingMenu.getName());
        String subTitleResourceKey = buildSubTitleResourceKey(existingMenu.getName());
        //修改标题
        langBiz.setLangValue(titleResourceKey,menu.getMenuTitleCnDesc(),LangTypeEnum.ZH_CN);
        langBiz.setLangValue(titleResourceKey,menu.getMenuTitleUsDesc(),LangTypeEnum.EN_US);

        //修改描述
        langBiz.setLangValue(subTitleResourceKey,menu.getMenuSubTitleCnDesc(),LangTypeEnum.ZH_CN);
        langBiz.setLangValue(subTitleResourceKey,menu.getMenuSubTitleUsDesc(),LangTypeEnum.EN_US);
    }

    public String buildTitleResourceKey(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        String[] parts = name.split("\\.");
        if (parts.length < 2) {
            return null; // 不符合预期格式
        }
        return "articles.pageTypes."+parts[parts.length - 1]+".title"; // 返回最后一个部分作为资源键
    }

    public String buildSubTitleResourceKey(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        String[] parts = name.split("\\.");
        if (parts.length < 2) {
            return null; // 不符合预期格式
        }
        return "articles.pageTypes."+parts[parts.length - 1]+".subtitle"; // 返回最后一个部分作为资源键
    }
}
