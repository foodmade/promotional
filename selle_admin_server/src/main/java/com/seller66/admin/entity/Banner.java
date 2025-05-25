package com.seller66.admin.entity;

import com.seller66.admin.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@ApiModel("")
@Table(name = "`banner`")
public class Banner extends BaseModel {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("")
    private Long id;

    /**
     * 标题
     */
    @Column(name = "`title`")
    @ApiModelProperty("标题")
    private String title;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 图片地址
     */
    @Column(name = "`image_url`")
    @ApiModelProperty("图片地址")
    private String imageUrl;

    /**
     * 跳转连接
     */
    @Column(name = "`link`")
    @ApiModelProperty("跳转连接")
    private String link;

    /**
     * 排序
     */
    @Column(name = "`sort`")
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 状态 1(正常) 0(禁用)
     */
    @Column(name = "`status`")
    @ApiModelProperty("状态 1(正常) 0(禁用)")
    private Integer status;
}