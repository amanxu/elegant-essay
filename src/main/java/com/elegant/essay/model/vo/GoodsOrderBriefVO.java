package com.elegant.essay.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-16 16:47
 */
@ApiModel(value = "订单简要信息信息")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoodsOrderBriefVO {

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单支付状态")
    private Integer payStatus;

    @ApiModelProperty(value = "订单交易状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单项列表")
    private List<GoodsOrderItemVO> orderItems;

}
