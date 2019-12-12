package com.elegant.essay.enums;

import lombok.Getter;

import java.io.Serializable;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-08-29 11:10
 */
@Getter
public enum ErrorCodeEnum implements Serializable {

    AUTH_ERR(900100, "鉴权失败"),
    JWT_EXPIRED_ERR(900101, "登陆超时"),
    BIZ_ERR(999999, "业务异常"),
    ADD_ERR(100100, "新增失败"),
    UPDATE_ERR(100101, "更新失败"),
    REMOVE_ERR(100102, "删除失败"),
    ART_NOT_EXIST_ERR(100103, "文章不存在"),
    USER_NOT_EXIST_ERR(100104, "用户不存在"),
    USER_EXIST_ERR(100105, "用户已存在"),
    USER_ADD_ERR(100106, "用户新增失败"),
    USER_MODIFY_ERR(100107, "用户修改失败"),
    OPERATE_USER_ERR(100108, "操作用户失败"),
    AUTH_ADD_USER_NULL_ERR(200100, "用户不能为空"),
    ACCOUNT_PWD_NULL_ERR(200100, "用户明或密码不能为空"),
    ACCOUNT_PWD_ERR(200101, "登陆密码错误"),
    ADDRESS_NOT_EXISTS_ERR(300001, "地址不存在"),
    ORDER_GOODS_NULL_ERR(400001, "下单商品不能为空"),
    ORDER_NOT_EXISTS_ERR(400002, "订单不存在"),
    ORDER_ITEM_NULL_ERR(400003, "订单项不存在"),
    USER_ADDRESS_ADD_ERR(500001, "收货地址新增失败"),
    USER_ADDRESS_MODIFY_ERR(500002, "收货地址修改失败"),
    USER_ADDRESS_NOT_EXISTS_ERR(500003, "收货地址不存在"),
    TRANS_MSG_STATE_ADD_ERR(600001, "新增事务消息状态日志失败"),
    TRANS_MSG_STATE_EXISTS_ERR(600002, "事务消息状态日志不存在"),
    TRANS_MSG_STATE_UPDATE_ERR(600003, "更新事务消息状态日志失败"),
    TRANS_MSG_CONSUME_LOG_ADD_ERR(600006, "新增消费事务消息日志失败"),
    TRANS_MSG_CONSUME_LOG_UPDATE_ERR(600007, "事务消息日志更新失败"),
    FILE_NOT_FOUND_ERR(800100, "文件不存在"),
    FILE_NULL_ERR(800101, "文件不能为空"),
    DATASOURCE_NULL_ERR(900001, "数据源不能为空"),
    SHARDING_RULE_CONF_ERR(900002, "分片规则配置错误"),
    DS_APP_BIZ_UID_CONF_NULL_ERR(900003, "分布式应用业务主键配置错误"),
    GOODS_STOCKS_ERR(900004, "商品库存不足"),
    ROCKET_MQ_SEND_FAIL(900005, "RocketMq消息发送失败"),
    TRANS_ROLLBACK_ERR(900006, "事务回滚");

    private Integer code;
    private String msg;

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(Integer code) {
        for (ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()) {
            if (errorCodeEnum.code.equals(code)) {
                return errorCodeEnum.getMsg();
            }
        }
        return null;
    }
}
