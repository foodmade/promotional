package com.seller66.admin.vo;

import com.seller66.admin.common.model.PageModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class BannerQueryVo extends PageModel {

    private Integer status;

    private String title;

}
