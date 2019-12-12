package com.elegant.essay.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-19 18:17
 */
@ApiModel(value = "用户查询基本参数")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserQuery extends IdQuery {

    @ApiModelProperty(value = "帐户名")
    private String account;

    @ApiModelProperty(value = "用户ID")
    private Long userId;
}
