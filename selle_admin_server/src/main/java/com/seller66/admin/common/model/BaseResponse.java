package com.seller66.admin.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {

    private int status = 200;

    private String message="success";


    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResponse() {
    }
}
