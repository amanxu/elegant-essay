package com.elegant.essay.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-16 19:16
 */
@ApiModel(value = "订单简要信息信息")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoodsOrderDetailVO extends GoodsOrderBriefVO {

    @ApiModelProperty(value = "订单总价")
    private BigDecimal price;

    @ApiModelProperty(value = "交易序号")
    private String tradeNum;

    @ApiModelProperty(value = "支付方式类型")
    private Integer payWay;

    @ApiModelProperty(value = "支付方式描述")
    private String payWayDesc;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "发货时间")
    private Date consignmentTime;

    @ApiModelProperty(value = "成交时间|订单确认收货时间")
    private Date completeTime;

    @ApiModelProperty(value = "用户ID")
    private Long userId;
}
