package com.seller66.admin.common.model;

import lombok.Data;

import java.util.Collection;

@Data
public class DataResponse<T> {

    private Integer pageSize;

    private Integer pageNum;

    private Long total;

    private Long pages;

    private Collection<T> rows;

}
