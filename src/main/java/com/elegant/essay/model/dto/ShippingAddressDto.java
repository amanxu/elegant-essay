package com.elegant.essay.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author aman
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddressDto implements Serializable {

    private static final long serialVersionUID = -4696844745901496276L;

    @ApiModelProperty(value = "地址记录ID，修改时必传，新增时忽略")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty(value = "区域ID")
    @NotNull(message = "区域ID不能为空")
    private Long areaId;

    @ApiModelProperty(value = "街道ID")
    @NotNull(message = "街道ID不能为空")
    private Long streetId;

    @ApiModelProperty(value = "邮政编码")
    private String zipCode;

    @ApiModelProperty(value = "详细地址")
    @NotNull(message = "详细地址不能为空")
    private String addressDesc;

    @ApiModelProperty(value = "收货人手机号")
    @NotNull(message = "收货人手机号不能为空")
    private String cellphone;

    @ApiModelProperty(value = "收货人姓名")
    @NotNull(message = "收货人姓名不能为空")
    private String consigneeName;

    @ApiModelProperty(value = "是否设置为默认收货地址0:普通地址,1:默认地址")
    private Integer isDefault;

}