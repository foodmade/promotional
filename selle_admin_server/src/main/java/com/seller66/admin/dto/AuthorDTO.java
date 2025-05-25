package com.seller66.admin.dto;

import com.seller66.admin.model.BaseModel;
import lombok.Data;

/**
* @description Author视图
* @author  jcb
* @since  2025-04-20 22:15:34
*/

@Data
public class AuthorDTO extends BaseModel {

    /**
    * id
    * 
    */
    private Long id;
    /**
    * name
    * 姓名
    */
    private String name;
    /**
    * avatar
    * 头像
    */
    private String avatar;
    /**
    * bio
    * 个人简介
    */
    private String bio;

    private String title;


}
