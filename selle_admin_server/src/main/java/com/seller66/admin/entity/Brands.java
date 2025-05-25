package com.seller66.admin.entity;

import com.seller66.admin.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import lombok.Data;

@Data
@ApiModel("")
@Table(name = "`brands`")
public class Brands extends BaseModel {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("")
    private Long id;

    /**
     * 名称
     */
    @Column(name = "`name`")
    @ApiModelProperty("名称")
    private String name;

    /**
     * slug
     */
    @Column(name = "`slug`")
    @ApiModelProperty("slug")
    private String slug;

    /**
     * logo
     */
    @Column(name = "`logo`")
    @ApiModelProperty("logo")
    private String logo;

    /**
     * 评级
     */
    @Column(name = "`rating`")
    @ApiModelProperty("评级")
    private Integer rating;

    /**
     * 描述
     */
    @Column(name = "`description`")
    @ApiModelProperty("描述")
    private String description;
}