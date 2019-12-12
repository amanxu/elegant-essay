package com.elegant.essay.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-16 16:14
 */
@ApiModel(value = "创建订单请求参数")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDto implements Serializable {

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty(value = "购买的商品ID集合")
    @NotEmpty(message = "商品ID不能为空")
    private List<CreateOrderItemDto> goodsInfos;

    @ApiModelProperty(value = "收货地址的ID")
    @NotNull(message = "收货人地址不能为空")
    private Long shippingAddressId;

    /*@ApiModelProperty(value = "收货地址信息")
    @NotNull(message = "收货地址不能为空")
    private String addressDesc;

    @ApiModelProperty(value = "收货人手机号")
    @NotNull(message = "收货人手机号不能为空")
    private String cellphone;

    @ApiModelProperty(value = "收货人姓名")
    @NotNull(message = "收货人姓名不能为空")
    private String consigneeName;*/

}
