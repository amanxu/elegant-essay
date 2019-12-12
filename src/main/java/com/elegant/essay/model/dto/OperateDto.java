package com.elegant.essay.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-10-20 11:12
 */
@Setter
@Getter
@ToString
public class OperateDto implements Serializable {

    @ApiModelProperty(value = "操作类型")
    @NotNull(message = "操作类型不能为空")
    private Integer operateType;

    @ApiModelProperty(value = "记录ID")
    @NotNull(message = "记录ID不能为空")
    private Long id;
}
