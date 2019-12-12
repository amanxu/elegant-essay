package com.elegant.essay.model;

import lombok.*;

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
public class ArtisanArea implements Serializable {
    private static final long serialVersionUID = 748960189709809211L;
    private Long areaId;

    private String areaName;

    private Long parentId;

    private Integer areaLevel;

    private Integer areaType;

    private Integer deleted;

    private String areaDesc;

    private Date createTime;

    private Date updateTime;

}