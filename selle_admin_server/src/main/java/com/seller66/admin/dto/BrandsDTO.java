package com.seller66.admin.dto;

import com.seller66.admin.model.BaseModel;
import lombok.Data;

/**
* @description Brands视图
* @author  jcb
* @since  2025-05-25 12:16:52
*/

@Data
public class BrandsDTO extends BaseModel {

    /**
    * id
    * 
    */
    private Long id;
    /**
    * name
    * 名称
    */
    private String name;
    /**
    * slug
    * slug
    */
    private String slug;
    /**
    * logo
    * logo
    */
    private String logo;
    /**
    * rating
    * 评级
    */
    private Integer rating;
    /**
    * description
    * 描述
    */
    private String description;


}
