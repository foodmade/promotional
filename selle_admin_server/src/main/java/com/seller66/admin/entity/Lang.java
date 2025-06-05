package com.seller66.admin.entity;

import com.seller66.admin.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@ApiModel("")
@Table(name = "`lang`")
public class Lang extends BaseModel {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("")
    private Long id;

    /**
     * 语言类型
     */
    @Column(name = "`type`")
    @ApiModelProperty("语言类型")
    private String type;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 语言包json格式
     */
    @Column(name = "`context`")
    @ApiModelProperty("语言包json格式")
    private String context;
}