package com.elegant.essay.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-22 20:04
 */
@ApiModel(value = "文章列表检索参数")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EssayQueryDto extends PageQuery implements Serializable {

    private static final long serialVersionUID = -5625983682412556208L;
    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章作者")
    private String userName;

    @ApiModelProperty(value = "文章作者ID")
    private String userId;
}
