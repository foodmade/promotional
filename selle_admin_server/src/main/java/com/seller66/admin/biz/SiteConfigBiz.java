package com.seller66.admin.biz;

import com.seller66.admin.mapper.SiteConfigMapper;
import com.seller66.admin.common.model.BaseBiz;
import com.seller66.admin.entity.SiteConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @description SiteConfig业务层
* @author  jcb
* @since  2025-06-05 18:07:43
*/
@Slf4j
@Service
@Transactional
public class SiteConfigBiz extends BaseBiz<SiteConfigMapper, SiteConfig> {

    public void updateConfig(SiteConfig siteConfig) {
        if(siteConfig.getId() == null){
            insert(siteConfig);
        }else{
            updateSelectiveById(siteConfig);
        }
    }

    public SiteConfig selectConfig() {
        return this.mapper.selectLastOne();
    }
}
