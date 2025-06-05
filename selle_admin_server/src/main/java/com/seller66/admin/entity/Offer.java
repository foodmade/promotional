package com.seller66.admin.entity;

import com.seller66.admin.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@ApiModel("")
@Table(name = "`offer`")
public class Offer extends BaseModel {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("")
    private Long id;

    @Column(name = "`popup_id`")
    @ApiModelProperty("")
    private String popupId;

    /**
     * 产品logo
     */
    @Column(name = "`logo`")
    @ApiModelProperty("产品logo")
    private String logo;

    /**
     * 产品标题
     */
    @Column(name = "`title`")
    @ApiModelProperty("产品标题")
    private String title;

    /**
     * 按钮文案
     */
    @Column(name = "`button_text`")
    @ApiModelProperty("按钮文案")
    private String buttonText;

    /**
     * 链接地址
     */
    @Column(name = "`offer_link`")
    @ApiModelProperty("链接地址")
    private String offerLink;

    /**
     * 用户数量
     */
    @Column(name = "`user_count`")
    @ApiModelProperty("用户数量")
    private Integer userCount;

    /**
     * 优惠金额
     */
    @Column(name = "`discount_amount`")
    @ApiModelProperty("优惠金额")
    private BigDecimal discountAmount;

    /**
     * 原价
     */
    @Column(name = "`original_price`")
    @ApiModelProperty("原价")
    private BigDecimal originalPrice;

    /**
     * 优惠价
     */
    @Column(name = "`special_price`")
    @ApiModelProperty("优惠价")
    private BigDecimal specialPrice;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    @ApiModelProperty("创建时间")
    private Date createTime;
}