package com.elegant.essay.model.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-27 16:41
 */
@ApiModel(value = "ES分页对象")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ElasticPage<T> implements Serializable {

    @ApiModelProperty(value = "页码")
    private Integer pageNo = 0;

    @ApiModelProperty(value = "分页尺寸")
    private Integer pageSize = 0;

    @ApiModelProperty(value = "分页对象")
    private Object content;

    @ApiModelProperty(value = "总条数")
    private Long totalElement = 0L;

    @ApiModelProperty(value = "总页数")
    private Integer totalPage = 0;

    public ElasticPage(Object content) {
        this.content = content;
    }
}
