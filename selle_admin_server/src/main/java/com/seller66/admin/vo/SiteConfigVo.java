package com.seller66.admin.vo;

import lombok.Data;

@Data
public class SiteConfigVo {

    //網站名稱
    private String siteName;

    //网站标题
    private String siteDescription;

    private String logoUrl;

    private String icp;

    private String siteKeywords;
}
