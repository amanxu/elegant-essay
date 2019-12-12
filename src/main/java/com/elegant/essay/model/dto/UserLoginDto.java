package com.elegant.essay.model.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-10-20 12:03
 */

@ApiModel(value = "用户登陆参数")
@Setter
@Getter
@ToString
public class UserLoginDto implements Serializable {

    @ApiModelProperty(value = "帐户名")
    private String username;

    @ApiModelProperty(value = "帐户密码")
    private String password;
}
