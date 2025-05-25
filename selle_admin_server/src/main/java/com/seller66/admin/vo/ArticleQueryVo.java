package com.seller66.admin.vo;

import com.seller66.admin.common.model.PageModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleQueryVo extends PageModel {

    private String title;

    private String tags;
}
