package com.elegant.essay.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-22 19:59
 */
@ApiModel(value = "分页查询基础参数")
@Setter
@Getter
public class PageQuery implements Serializable {

    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不能为空")
    @Min(value = 1)
    private Integer pageNo;

    @ApiModelProperty(value = "每页显示数量")
    @NotNull(message = "每页显示尺寸")
    @Min(value = 5)
    private Integer pageSize;
}
