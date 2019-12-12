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
 * @date: 2018-09-22 20:02
 */
@ApiModel(value = "ID入参")
@Setter
@Getter
@ToString
public class IdQuery implements Serializable {

    @ApiModelProperty(value = "对象的ID")
    private Long id;
}
