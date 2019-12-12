package com.elegant.essay.enums;

import lombok.Getter;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-08 10:20
 */
@Getter
public enum BizTypeEnum {

    ORDER_ID("GOODS_ORDER_ID", "订单ID"),
    ORDER_ITEM_ID("GOODS_ORDER_ITEM_ID", "订单项ID");

    private String code;
    private String msg;

    BizTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(String code) {
        for (BizTypeEnum statusEnum : BizTypeEnum.values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum.msg;
            }
        }
        return null;
    }
}
