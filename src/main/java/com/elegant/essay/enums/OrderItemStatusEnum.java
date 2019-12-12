package com.elegant.essay.enums;

import lombok.Getter;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-08 10:20
 */
@Getter
public enum OrderItemStatusEnum {

    INIT(0, "初始化"),
    NORMAL(1, "交易中"),
    COMPLETE(2, "交易完成"),
    REFUNDING(3, "退款中"),
    REFUNDED(4, "已退款");

    private Integer code;
    private String msg;

    OrderItemStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(Integer code) {
        for (OrderItemStatusEnum statusEnum : OrderItemStatusEnum.values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum.msg;
            }
        }
        return null;
    }
}
