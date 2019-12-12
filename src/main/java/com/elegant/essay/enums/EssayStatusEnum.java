package com.elegant.essay.enums;

import lombok.Getter;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-08 10:20
 */
@Getter
public enum EssayStatusEnum {

    DRAFT("draft", "草稿"),
    PUBLISHED("published", "已发布");

    private String code;
    private String msg;

    EssayStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(String code) {
        for (EssayStatusEnum statusEnum : EssayStatusEnum.values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum.msg;
            }
        }
        return null;
    }
}
