package com.seller66.admin.biz;

import com.seller66.admin.common.enmu.LangTypeEnum;
import com.seller66.admin.mapper.LangMapper;
import com.seller66.admin.common.model.BaseBiz;
import com.seller66.admin.entity.Lang;
import com.seller66.admin.utils.LangUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
