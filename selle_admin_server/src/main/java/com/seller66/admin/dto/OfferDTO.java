package com.seller66.admin.dto;

import com.seller66.admin.model.BaseModel;
        import java.math.BigDecimal;
        import java.util.Date;
import lombok.Data;

/**
* @description Offer视图
* @author  jcb
* @since  2025-05-26 17:17:50
*/

@Data
public class OfferDTO extends BaseModel {

    /**
    * id
    * 
    */
    private Long id;
    /**
    * popupId
    * 
    */
    private String popupId;
    /**
    * logo
    * 产品logo
    */
    private String logo;
    /**
    * title
    * 产品标题
    */
    private String title;
    /**
    * buttonText
    * 按钮文案
    */
    private String buttonText;
    /**
    * offerLink
    * 链接地址
    */
    private String offerLink;
    /**
    * userCount
    * 用户数量
    */
    private Integer userCount;
    /**
    * discountAmount
    * 优惠金额
    */
    private BigDecimal discountAmount;
    /**
    * originalPrice
    * 原价
    */
    private BigDecimal originalPrice;
    /**
    * specialPrice
    * 优惠价
    */
    private BigDecimal specialPrice;
    /**
    * createTime
    * 创建时间
    */
    private Date createTime;


}
