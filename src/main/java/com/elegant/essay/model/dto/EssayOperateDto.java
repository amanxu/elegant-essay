package com.elegant.essay.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-22 21:26
 */
@ApiModel(value = "文章上架|下架")
@Setter
@Getter
public class EssayOperateDto extends IdQuery implements Serializable {

    @ApiModelProperty(value = "操作类型|0:上架1:下架")
    @NotNull(message = "操作类型不能为空")
    private String operateType;

}
