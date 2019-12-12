package com.elegant.essay.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-16 16:37
 */
@ApiModel(value = "订单项信息")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoodsOrderItemVO implements Serializable {

    @ApiModelProperty(value = "订单项ID")
    private Long orderItemId;

    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品图标")
    private String goodsIcon;

    @ApiModelProperty(value = "商品成交价")
    private BigDecimal price;

    @ApiModelProperty(value = "购买数量")
    private Integer quantity;

}
