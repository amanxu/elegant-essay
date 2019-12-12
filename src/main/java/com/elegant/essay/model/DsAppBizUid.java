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
public class DsAppBizUid implements Serializable {
    private static final long serialVersionUID = 4043539379199448053L;
    private Integer id;

    private String appName;

    private String bizName;

    private Long bizUid;

    private Date createTime;

    private Date updateTime;

}