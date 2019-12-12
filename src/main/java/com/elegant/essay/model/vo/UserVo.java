package com.elegant.essay.model.vo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaoxu.nie
 */
@Slf4j
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {

    private static final long serialVersionUID = -1879459637649116124L;
    private Long userId;

    private String userName;

    private String realName;

    private String password;

    private String phone;

    private String email;

    private Integer userType;

    private String userTypeDesc;

    private Integer deleted;

    private List<String> roles;

}