package com.elegant.essay.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-16 20:10
 */
@ApiModel(value = "商品订单分页参数")
@Setter
@Getter
public class GoodsOrderPageParam extends PageQuery {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "商品名称", hidden = true)
    private String goodsName;
}
