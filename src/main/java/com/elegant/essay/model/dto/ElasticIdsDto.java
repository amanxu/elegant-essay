package com.elegant.essay.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-28 18:36
 */
@ApiModel(value = "ID集合封装类")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ElasticIdsDto implements Serializable {

    @ApiModelProperty(value = "ID集合")
    @NotEmpty(message = "ID集合不能为空")
    private List<Long> ids;
}
