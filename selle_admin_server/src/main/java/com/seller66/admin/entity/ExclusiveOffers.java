package com.seller66.admin.entity;

import com.seller66.admin.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@ApiModel("")
@Table(name = "`exclusive_offers`")
public class ExclusiveOffers extends BaseModel {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("")
    private Long id;

    /**
     * 图片地址
     */
    @Column(name = "`logo`")
    @ApiModelProperty("图片地址")
    private String logo;

    /**
     * 跳转链接
     */
    @Column(name = "`link`")
    @ApiModelProperty("跳转链接")
    private String link;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    @ApiModelProperty("创建时间")
    private Date createTime;
}