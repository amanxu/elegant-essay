package com.elegant.essay.enums;

import lombok.Getter;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-08 10:20
 */
@Getter
public enum PayStatusEnum {

    NON_PAY(0, "未支付"),
    PAY_SUC(1, "支付成功");

    private Integer code;
    private String msg;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(Integer code) {
        for (PayStatusEnum statusEnum : PayStatusEnum.values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum.msg;
            }
        }
        return null;
    }
}
