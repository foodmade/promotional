package com.seller66.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UploadDTO implements Serializable {

    private String url;

}
