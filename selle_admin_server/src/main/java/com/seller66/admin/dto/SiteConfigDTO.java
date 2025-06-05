package com.seller66.admin.dto;

import com.seller66.admin.model.BaseModel;
        import java.util.Date;
import lombok.Data;

/**
* @description SiteConfig视图
* @author  jcb
* @since  2025-06-05 18:07:43
*/

@Data
public class SiteConfigDTO extends BaseModel {

    /**
    * id
    * 主键
    */
    private Integer id;
    /**
    * siteName
    * 网站标题

    */
    private String siteName;
    /**
    * siteKeywords
    * 网站关键词
    */
    private String siteKeywords;
    /**
    * logoUrl
    * 网站logo
    */
    private String logoUrl;
    /**
    * icp
    * 网站icp
    */
    private String icp;
    /**
    * createTime
    * 创建时间
    */
    private Date createTime;
    /**
    * siteDescription
    * 网站描述
    */
    private String siteDescription;


}
