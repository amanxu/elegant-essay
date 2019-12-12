package com.elegant.essay.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019-01-08 14:43
 */
@Slf4j
public class DynamicProxyMain {

    public static void consumer(IUserService userService) {
        String userName = userService.getUserName();
        String userNameById = userService.getUserNameById("1");
        log.info("userName:{} ", userName);
    }

    public static void main(String[] args) throws Throwable {

        // 动态代理
        ClassLoader classLoader = IUserService.class.getClassLoader();
        Class<?>[] interfaces = new Class[]{IUserService.class};
        InvocationHandler handler = new DynamicProxyHandler(new UserServiceImpl());
        IUserService userService = (IUserService) Proxy.newProxyInstance(classLoader, interfaces, handler);

        /*IUserService userService = (IUserService) new DynamicProxyHandler().createInstance(new UserServiceImpl());*/
        consumer(userService);
    }
}
