package com.seller66.admin.dto;

import com.seller66.admin.model.BaseModel;
        import java.math.BigDecimal;
        import java.util.Date;
import lombok.Data;

/**
* @description SellerTools视图
* @author  jcb
* @since  2025-05-23 14:16:36
*/

@Data
public class SellerToolsDTO extends BaseModel {

    /**
    * id
    * 
    */
    private Long id;
    /**
    * name
    * 产品名称
    */
    private String name;
    /**
    * image
    * 产品图片
    */
    private String image;
    /**
    * rating
    * 评分
    */
    private BigDecimal rating;
    /**
    * price
    * 最低价格
    */
    private BigDecimal price;
    /**
    * purchaseCount
    * 评论数
    */
    private Long purchaseCount;
    /**
    * freeTrialDays
    * 免费天数
    */
    private Integer freeTrialDays;
    /**
    * freeTrialLink
    * 跳转链接
    */
    private String freeTrialLink;
    /**
    * detailedPricing
    * 价格描述
    */
    private String detailedPricing;
    /**
    * website
    * 官网地址
    */
    private String website;
    /**
    * reviewLink
    * 文章地址
    */
    private String reviewLink;
    /**
    * createTime
    * 创建时间
    */
    private Date createTime;
    /**
    * rank
    * 排名
    */
    private Integer rank;
    /**
    * type
    * 类型
    */
    private String type;
    /**
    * shortDescription
    * 描述
    */
    private String shortDescription;
    /**
    * highlights
    * 产品要点 数组格式
    */
    private String highlights;
    /**
    * detailedFeatures
    * 产品详细特征 数组格式
    */
    private String detailedFeatures;


}
