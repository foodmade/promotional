package com.seller66.admin.dto;

import com.seller66.admin.model.BaseModel;
        import java.util.Date;
import lombok.Data;

/**
* @description Support视图
* @author  jcb
* @since  2025-05-25 13:05:56
*/

@Data
public class SupportDTO extends BaseModel {

    /**
    * id
    * 
    */
    private Long id;
    /**
    * type
    * 类型 (wechat,qq)
    */
    private String type;
    /**
    * title
    * 标题
    */
    private String title;
    /**
    * description
    * 描述
    */
    private String description;
    /**
    * qrcode
    * 二维码图片地址
    */
    private String qrcode;
    /**
    * createTime
    * 创建时间
    */
    private Date createTime;


}
