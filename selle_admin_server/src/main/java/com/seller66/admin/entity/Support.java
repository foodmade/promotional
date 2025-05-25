package com.seller66.admin.entity;

import com.seller66.admin.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@ApiModel("")
@Table(name = "`support`")
public class Support extends BaseModel {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("")
    private Long id;

    /**
     * 类型 (wechat,qq)
     */
    @Column(name = "`type`")
    @ApiModelProperty("类型 (wechat,qq)")
    private String type;

    /**
     * 标题
     */
    @Column(name = "`title`")
    @ApiModelProperty("标题")
    private String title;

    /**
     * 描述
     */
    @Column(name = "`description`")
    @ApiModelProperty("描述")
    private String description;

    /**
     * 二维码图片地址
     */
    @Column(name = "`qrCode`")
    @ApiModelProperty("二维码图片地址")
    private String qrcode;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    @ApiModelProperty("创建时间")
    private Date createTime;
}