package com.elegant.essay.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-08 14:45
 */
@Slf4j
public class UserServiceImpl implements IUserService {

    @Override
    public String getUserName() {
        return "[用户名]：xiaoxu.nie";
    }

    @Override
    public String getUserNameById(String id) {
        return "xiaoxu.nie";
    }
}
