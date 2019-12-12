package com.elegant.essay.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoxu.nie
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ElegantEssay implements Serializable {

    private Long essayId;

    private String title;

    private Date publishTime;

    private String summary;

    private Integer allowComment;

    private Integer sort;

    private String content;

    private String imgUrl;

    private String status;

    private Integer deleted;

    private Long userId;

    private Integer categoryId;

    private Date createTime;

    private Date updateTime;
}