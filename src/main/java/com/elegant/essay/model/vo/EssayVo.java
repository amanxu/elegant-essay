package com.elegant.essay.model.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-22 20:48
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EssayVo implements Serializable {

    private Long essayId;

    private String title;

    private Date publishTime;

    private String publishDesc;

    private String summary;

    private Integer allowComment;

    private Integer sort;

    private String imgUrl;

    private String content;

    private String status;

    private String statusDesc;

    private Long userId;

    private String userName;

    private Date createTime;

}
