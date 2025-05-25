package com.seller66.admin.common.model;

import com.github.pagehelper.PageInfo;
import com.seller66.admin.utils.ConvertUtil;
import lombok.Data;

import java.util.Collection;
import java.util.HashMap;

@Data
public class ObjectRestResponse<T> extends BaseResponse {

    private T data;

    boolean rel;

    public ObjectRestResponse(int status, String message, T data) {
        super(status, message);
        this.data = data;
    }

    public ObjectRestResponse(int status, String message) {
        super(status, message);
    }

    public ObjectRestResponse(T data) {
        this.data = data;
    }

    public ObjectRestResponse() {
    }

    public ObjectRestResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    public ObjectRestResponse<T> rel(boolean rel) {
        this.rel = rel;
        return this;
    }

    public static <T> ObjectRestResponse<T> build(int status, String message, T data) {
        return new ObjectRestResponse<>(status, message, data);
    }

    public static <T> ObjectRestResponse<T> build(int status, String message) {
        return new ObjectRestResponse<>(status, message);
    }


    public static <T> ObjectRestResponse<T> build(T data) {
        if(data == null){
            return empty();
        }
        return new ObjectRestResponse<>(data);
    }


    public static <T> ObjectRestResponse<T> build() {
        return empty();
    }

    public static <T> ObjectRestResponse<T> empty(){
        ObjectRestResponse response = new ObjectRestResponse<>();
        response.setData(new HashMap<>());
        return response;
    }

    public static <T> ObjectRestResponse convertBuild(int status, String message, Object data, Class<T> tClass) {
        if (data.getClass().getTypeName().equals(tClass.getTypeName())) {
            return new ObjectRestResponse(status, message, data);
        } else if (data instanceof PageInfo) {
            return new ObjectRestResponse(status, message, ConvertUtil.convertPage((PageInfo) data, tClass));
        } else if (data instanceof Collection) {
            return new ObjectRestResponse(status, message, ConvertUtil.convertList((Collection) data, tClass));
        } else {
            return new ObjectRestResponse(ConvertUtil.convert(data, tClass));
        }
    }

    public static <T> ObjectRestResponse<T> convertBuild(Object data, Class<T> tClass) {
        return ObjectRestResponse.convertBuild(200, "success", data, tClass);
    }

}
