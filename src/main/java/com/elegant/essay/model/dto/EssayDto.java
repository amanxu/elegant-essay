package com.elegant.essay.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-22 20:02
 */
@ApiModel(value = "文章入参")
@Setter
@Getter
@ToString
public class EssayDto implements Serializable {

    @ApiModelProperty(value = "数据库记录ID")
    private Long essayId;

    @ApiModelProperty(value = "标题")
    @NotNull(message = "文章标题")
    private String title;

    @ApiModelProperty(value = "发布时间")
    @NotNull(message = "发布时间不能为空")
    private Date publishTime;

    @ApiModelProperty(value = "文章摘要")
    private String summary;

    @ApiModelProperty(value = "是否允许评论")
    private Integer allowComment;

    @ApiModelProperty(value = "文章权重")
    private Integer sort;

    @ApiModelProperty(value = "配图")
    private String imgUrl;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "状态发布|草稿")
    private String status;

    @ApiModelProperty(value = "作者ID")
    private Long userId;

    @ApiModelProperty(value = "所属分类ID")
    private Integer categoryId=6;

}
