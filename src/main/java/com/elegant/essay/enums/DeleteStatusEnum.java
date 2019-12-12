package com.elegant.essay.enums;

import lombok.Getter;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-08 10:20
 */
@Getter
public enum DeleteStatusEnum {

    NORMAL(0, "正常"),
    DELETED(1, "已删除");

    private Integer code;
    private String msg;

    DeleteStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(Integer code) {
        for (DeleteStatusEnum statusEnum : DeleteStatusEnum.values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum.msg;
            }
        }
        return null;
    }
}
