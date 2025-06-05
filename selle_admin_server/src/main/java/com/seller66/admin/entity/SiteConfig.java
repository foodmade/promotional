package com.seller66.admin.entity;

import com.seller66.admin.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@ApiModel("通用网站配置表")
@Table(name = "`site_config`")
public class SiteConfig extends BaseModel {
    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 网站标题

     */
    @Column(name = "`site_name`")
    @ApiModelProperty("网站标题")
    private String siteName;

    /**
     * 网站关键词
     */
    @Column(name = "`site_keywords`")
    @ApiModelProperty("网站关键词")
    private String siteKeywords;

    /**
     * 网站logo
     */
    @Column(name = "`logo_url`")
    @ApiModelProperty("网站logo")
    private String logoUrl;

    /**
     * 网站icp
     */
    @Column(name = "`icp`")
    @ApiModelProperty("网站icp")
    private String icp;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 网站描述
     */
    @Column(name = "`site_description`")
    @ApiModelProperty("网站描述")
    private String siteDescription;
}