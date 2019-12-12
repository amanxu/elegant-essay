package com.elegant.essay.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-28 13:04
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EssayElasticQueryDto extends PageQuery {

    @ApiModelProperty(value = "ES标题检索")
    private String title;

    @ApiModelProperty(value = "ES全文检索字段")
    private String content;

    @ApiModelProperty(value = "ES作者检索")
    private String userName;

    @ApiModelProperty(value = "ES文章分类检索")
    private String categoryName;
}
