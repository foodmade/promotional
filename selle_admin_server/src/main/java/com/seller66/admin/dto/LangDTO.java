package com.seller66.admin.dto;

import com.seller66.admin.model.BaseModel;
        import java.util.Date;
import lombok.Data;

/**
* @description Lang视图
* @author  jcb
* @since  2025-06-05 19:48:24
*/

@Data
public class LangDTO extends BaseModel {

    /**
    * id
    * 
    */
    private Long id;
    /**
    * type
    * 语言类型
    */
    private String type;
    /**
    * createTime
    * 创建时间
    */
    private Date createTime;
    /**
    * context
    * 语言包json格式
    */
    private String context;


}
