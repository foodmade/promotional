package com.seller66.admin.common.enmu;

import lombok.Getter;

@Getter
public enum LangTypeEnum {
    ZH_CN("zh-CN"),
    EN_US("en-US"),
    ;

    private String type;

    LangTypeEnum(String type) {
        this.type = type;
    }
}
