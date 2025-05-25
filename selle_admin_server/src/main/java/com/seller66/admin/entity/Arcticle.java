package com.seller66.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.seller66.admin.model.BaseModel;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "`arcticle`")
public class Arcticle extends BaseModel {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题
     */
    @Column(name = "`title`")
    private String title;

    /**
     * 文章路径
     */
    @Column(name = "`slug`")
    private String slug;

    /**
     * 标签列表
     */
    @Column(name = "`tags`")
    private String tags;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 更新日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "`update_time`")
    private Date updateTime;

    /**
     * 精选图片url
     */
    @Column(name = "`featured_image`")
    private String featuredImage;

    /**
     * 发布者信息
     */
    @Column(name = "`author`")
    private Long author;

    /**
     * 图片地址
     */
    @Column(name = "`image`")
    private String image;

    @Column(name = "`read_time`")
    private String readTime;

    /**
     * 文章摘要
     */
    @Column(name = "`excerpt`")
    private String excerpt;

    /**
     * 文章内容
     */
    @Column(name = "`content`")
    private String content;
}