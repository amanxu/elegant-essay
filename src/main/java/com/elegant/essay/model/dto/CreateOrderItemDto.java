package com.elegant.essay.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-16 21:31
 */
@ApiModel(value = "创建订单项请求参数")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderItemDto implements Serializable {

    @ApiModelProperty(value = "商品ID")
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;

    @ApiModelProperty(value = "购买数量")
    @NotNull(message = "商品数量")
    private Integer quantity;
}
