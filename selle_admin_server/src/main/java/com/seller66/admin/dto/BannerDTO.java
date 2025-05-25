package com.seller66.admin.dto;

import com.seller66.admin.model.BaseModel;
        import java.util.Date;
import lombok.Data;

/**
* @description Banner视图
* @author  jcb
* @since  2025-05-25 11:10:34
*/

@Data
public class BannerDTO extends BaseModel {

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
    * createTime
    * 创建时间
    */
    private Date createTime;
    /**
    * imageUrl
    * 图片地址
    */
    private String imageUrl;
    /**
    * link
    * 跳转连接
    */
    private String link;
    /**
    * sort
    * 排序
    */
    private Integer sort;
    /**
    * status
    * 状态 1(正常) 0(禁用)
    */
    private Integer status;


}
