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
public class ElegantUser implements Serializable {
    private static final long serialVersionUID = 341848751297278290L;
    private Long userId;

    private String userName;

    private String password;

    private String realName;

    private String phone;

    private String email;

    private Integer userType;

    private Integer deleted;

    private Date createTime;

    private Date updateTime;

}