package com.elegant.essay.netty.http;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-05 18:15
 */
@Setter
@Getter
@ToString
public class UserLoginRequest extends HttpMsg {

    private String userName;

    private String password;
}
