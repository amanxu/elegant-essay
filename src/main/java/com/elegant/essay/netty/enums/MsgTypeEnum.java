package com.elegant.essay.netty.enums;

import lombok.Getter;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-05 18:11
 */
@Getter
public enum MsgTypeEnum {
    USER_LOGIN(100001, "用户登陆"),
    USER_DETAIL(100002, "用户详情");

    private Integer msgType;

    private String desc;

    MsgTypeEnum(Integer msgType, String desc) {
        this.msgType = msgType;
        this.desc = desc;
    }

    public static String getDescByType(Integer msgType) {
        for (MsgTypeEnum msgTypeEnum : MsgTypeEnum.values()) {
            if (msgTypeEnum.msgType.equals(msgType)) {
                return msgTypeEnum.desc;
            }
        }
        return null;
    }
}
