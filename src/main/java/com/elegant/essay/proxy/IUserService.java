package com.elegant.essay.proxy;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-08 14:44
 */
public interface IUserService {

    /**
     * 获取用户名
     *
     * @return
     */
    String getUserName();

    /**
     * 获取用户名
     *
     * @param id
     * @return
     */
    String getUserNameById(String id);
}
