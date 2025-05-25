package com.seller66.admin.common.model;

import com.github.pagehelper.PageInfo;
import com.seller66.admin.utils.ConvertUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
public class TableRestResponse<T> extends BaseResponse {

    private DataResponse<T> data;

    public TableRestResponse() {
        super();
        this.data = new DataResponse<>();
    }

    public TableRestResponse(Collection<T> collection) {
        super();
        DataResponse<T> data = new DataResponse<>();
        data.setPageNum(1);
        data.setTotal(Long.parseLong(collection.size() + ""));
        data.setPageSize(collection.size());
        data.setRows(collection);
        this.data = data;
    }


    public TableRestResponse(int status, String message, int pageNum, int pageSize, long total, Collection<T> collection) {
        super(status, message);
        DataResponse<T> data = new DataResponse<>();
        data.setPageNum(pageNum);
        data.setTotal(total);
        data.setPageSize(pageSize);
        data.setRows(collection);
        this.data = data;
    }
    public TableRestResponse(int pageNum, int pageSize, long total,long pages, Collection<T> collection) {
        super();
        DataResponse<T> data = new DataResponse<>();
        data.setPageNum(pageNum);
        data.setTotal(total);
        data.setPageSize(pageSize);
        data.setRows(collection);
        data.setPages(pages);
        this.data = data;
    }

    public TableRestResponse(int pageNum, int pageSize, long total, Collection<T> collection) {
        super();
        DataResponse<T> data = new DataResponse<>();
        data.setPageNum(pageNum);
        data.setTotal(total);
        data.setPageSize(pageSize);
        data.setRows(collection);
        this.data = data;
    }

    public TableRestResponse(int pageNum, int pageSize, long total) {
        DataResponse<T> data = new DataResponse<>();
        data.setPageNum(pageNum);
        data.setTotal(total);
        data.setPageSize(pageSize);
        this.data = data;
    }

    public TableRestResponse(int status, String message) {
        super(status, message);
    }


    public static <T> TableRestResponse<T> build(int status, String message, int pageNum, int pageSize, long total, Collection<T> collection) {
        return new TableRestResponse(status, message, pageNum, pageSize, total, collection);
    }

    public static <T> TableRestResponse<T> build(int status, String message, int pageNum, int pageSize, Collection<T> collection) {
        return new TableRestResponse(status, message, pageNum, pageSize, collection.size(), collection);
    }

    public static <T> TableRestResponse<T> build(int pageNum, int pageSize, long total, Collection<T> collection) {
        return new TableRestResponse(pageNum, pageSize, total, collection);
    }
    public static <T> TableRestResponse<T> build(int pageNum, int pageSize, long total,long pages, Collection<T> collection) {
        return new TableRestResponse(pageNum, pageSize, total,pages, collection);
    }

    public static <T> TableRestResponse<T> build(int pageNum, int pageSize, Collection<T> collection) {
        return new TableRestResponse(pageNum, pageSize, collection.size(), collection);
    }

    public static <T> TableRestResponse<T> build(int status, String message) {
        return new TableRestResponse(status, message);
    }

    public static <T> TableRestResponse<T> build(Collection<T> collection) {
        return new TableRestResponse(collection);
    }

    public static <T> TableRestResponse<T> build() {
        return new TableRestResponse();
    }


    public static <T> TableRestResponse<T> convertBuild(int status, String message, int pageNum, int pageSize, Object data, Class<T> tClass) throws NoSuchFieldException{
        if (data.getClass().getTypeName().equals(tClass.getTypeName())) {
            List<T> arr = new ArrayList<>();
            arr.add((T) data);
            return TableRestResponse.build(status, message, pageNum, pageSize, arr);
        } else if (data instanceof PageInfo) {
            return TableRestResponse.build(status, message, pageNum, pageSize, ((PageInfo) data).getTotal(), ConvertUtil.convertList(((PageInfo) data).getList(), tClass));
        } else if (data instanceof Collection) {
            return TableRestResponse.build(status, message, pageNum, pageSize, ConvertUtil.convertList((Collection) data, tClass));
        } else {
            List<T> arr = new ArrayList<>();
            arr.add(ConvertUtil.convert(data, tClass));
            return TableRestResponse.build(status, message, pageNum, pageSize, arr);
        }
    }

    public static <T> TableRestResponse<T> convertBuild(Object data, Class<T> tClass) {
        try {
            return TableRestResponse.convertBuild(200, "success", 0, 0, data, tClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> TableRestResponse<T> convertBuild(int pageNum, int pageSize, Object data, Class<T> tClass) {
        try {
            return TableRestResponse.convertBuild(200, "success", pageNum, pageSize, data, tClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> TableRestResponse<T> convertBuild(PageModel pageModel, Object data, Class<T> tClass) {
        try {
            return TableRestResponse.convertBuild(200, "success", pageModel.getPageNum(), pageModel.getPageSize(), data, tClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
