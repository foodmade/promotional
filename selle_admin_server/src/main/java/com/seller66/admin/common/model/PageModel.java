package com.seller66.admin.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jcb on 2020-04-26 14:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageModel extends BaseParamsModel {

    private int pageNum = 1;

    private int pageSize = 10;
}
