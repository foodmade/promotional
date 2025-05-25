package com.seller66.admin.dto;

import com.seller66.admin.model.BaseModel;
import java.util.Date;
import lombok.Data;

/**
* @description Arcticle视图
* @author  jcb
* @since  2025-05-22 13:05:45
*/

@Data
public class ArcticleDTO extends BaseModel {

    /**
    * id
    * 
    */
    private Long id;
    /**
    * title
    * 标题
    */
    private String title;
    /**
    * slug
    * 文章路径
    */
    private String slug;
    /**
    * tags
    * 标签列表
    */
    private String tags;
    /**
    * createTime
    * 创建日期
    */
    private Date createTime;
    /**
    * updateTime
    * 更新日期
    */
    private Date updateTime;
    /**
    * featuredImage
    * 精选图片url
    */
    private String featuredImage;
    /**
    * author
    * 发布者信息
    */
    private Long author;
    /**
    * image
    * 图片地址
    */
    private String image;
    /**
    * readTime
    * 
    */
    private String readTime;
    /**
    * excerpt
    * 文章摘要
    */
    private String excerpt;
    /**
    * content
    * 文章内容
    */
    private String content;


}
