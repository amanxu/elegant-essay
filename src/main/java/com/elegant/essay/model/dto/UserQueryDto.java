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
 * @date: 2018-09-23 22:53
 */
@ApiModel(value = "用户列表过滤参数")
@Setter
@Getter
@ToString
public class UserQueryDto extends PageQuery implements Serializable {

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "登陆帐户名")
    private String userName;
}
