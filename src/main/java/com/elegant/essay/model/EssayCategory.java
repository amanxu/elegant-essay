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
public class EssayCategory implements Serializable {

    private static final long serialVersionUID = -5820366755277388634L;
    private Integer id;

    private String categoryName;

    private String iconUrl;

    private Integer sort;

    private Integer parentId;

    private Integer level;

    private Integer deleted;

    private Date createTime;

    private Date updateTime;

}