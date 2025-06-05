package com.seller66.admin.dto;

import com.seller66.admin.model.BaseModel;
        import java.util.Date;
import lombok.Data;

/**
* @description ExclusiveOffers视图
* @author  jcb
* @since  2025-05-26 17:54:23
*/

@Data
public class ExclusiveOffersDTO extends BaseModel {

    /**
    * id
    * 
    */
    private Long id;
    /**
    * logo
    * 图片地址
    */
    private String logo;
    /**
    * link
    * 跳转链接
    */
    private String link;
    /**
    * createTime
    * 创建时间
    */
    private Date createTime;


}
