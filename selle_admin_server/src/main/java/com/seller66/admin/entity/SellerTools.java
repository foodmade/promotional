package com.seller66.admin.entity;

import com.seller66.admin.model.BaseModel;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "`seller_tools`")
public class SellerTools extends BaseModel {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 产品名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 产品图片
     */
    @Column(name = "`image`")
    private String image;

    /**
     * 评分
     */
    @Column(name = "`rating`")
    private BigDecimal rating;

    /**
     * 最低价格
     */
    @Column(name = "`price`")
    private BigDecimal price;

    /**
     * 评论数
     */
    @Column(name = "`purchase_count`")
    private Long purchaseCount;

    /**
     * 免费天数
     */
    @Column(name = "`free_trial_days`")
    private Integer freeTrialDays;

    /**
     * 跳转链接
     */
    @Column(name = "`free_trial_link`")
    private String freeTrialLink;

    /**
     * 价格描述
     */
    @Column(name = "`detailed_pricing`")
    private String detailedPricing;

    /**
     * 官网地址
     */
    @Column(name = "`website`")
    private String website;

    /**
     * 文章地址
     */
    @Column(name = "`review_link`")
    private String reviewLink;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 排名
     */
    @Column(name = "`rank`")
    private Integer rank;

    /**
     * 类型
     */
    @Column(name = "`type`")
    private String type;

    /**
     * 描述
     */
    @Column(name = "`short_description`")
    private String shortDescription;

    /**
     * 产品要点 数组格式
     */
    @Column(name = "`highlights`")
    private String highlights;

    /**
     * 产品详细特征 数组格式
     */
    @Column(name = "`detailed_features`")
    private String detailedFeatures;
}