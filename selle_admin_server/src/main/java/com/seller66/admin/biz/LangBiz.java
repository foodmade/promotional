package com.seller66.admin.biz;

import com.seller66.admin.common.enmu.LangTypeEnum;
import com.seller66.admin.mapper.LangMapper;
import com.seller66.admin.common.model.BaseBiz;
import com.seller66.admin.entity.Lang;
import com.seller66.admin.utils.LangUtil;
import com.seller66.admin.vo.LangModelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
* @description Lang业务层
* @author  jcb
* @since  2025-06-05 19:48:24
*/
@Slf4j
@Service
@Transactional
public class LangBiz extends BaseBiz<LangMapper, Lang> {

    public String langFindByKey(String key, LangTypeEnum type) {
        List<Lang> langList = this.mapper.selectAll();
        return LangUtil.findValueByTypeAndKey(langList,type,key);
    }

    public void setLangValue(String key, String value, LangTypeEnum type) {
        List<Lang> langList = this.mapper.selectAll();
        LangUtil.setValueByTypeAndKey(langList, type, key, value);
        for (Lang lang : langList) {
            updateSelectiveById(lang);
        }
    }

    public HashMap<String, Object> getAllLangsMap() {
        List<Lang> langList = this.mapper.selectAll();
        HashMap<String, Object> langMap = new HashMap<>();
        for (Lang lang : langList) {
            if (lang.getType() == null) {
                continue;
            }
            langMap.put(lang.getType(), lang.getContext());
        }
        return langMap;
    }

    public void updateLang(LangModelVo langModelVo) {
        updateLangByType(LangTypeEnum.ZH_CN,langModelVo.getZh());
        updateLangByType(LangTypeEnum.EN_US,langModelVo.getEn());
    }

    public void updateLangByType(LangTypeEnum type, String value) {
        this.mapper.updateByType(type.getType(), value);
    }
}
