package com.seller66.admin.common.api;

import lombok.Data;

import java.util.List;

@Data
public class CommonResultList<T> {
    public long code = 200;

    public String message;

    private List<T> data;

    private Long total;

    private Integer pageNum;

    private Integer pageSize;

    protected CommonResultList(long code, String message, List<T> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonResultList(List<T> data, Long total, Integer pageNum, Integer pageSize) {
        this.data = data;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public static <T> CommonResultList<T> success(List<T> list, Long total, Integer pageNum, Integer pageSize) {
        CommonResultList<T> result = new CommonResultList<>(list, total, pageNum, pageSize);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        return result;
    }

    public static <T> CommonResultList<T> success(List<T> list){
        CommonResultList<T> result = new CommonResultList<T>(list,0L,0,0);
        return result;
    }

}
