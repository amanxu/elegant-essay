package com.elegant.essay.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限限制
 *
 * @author xiaoxu.nie
 * @date 2018-10-12
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermessionLimit {

    /**
     * 登录拦截 (默认拦截)
     */
    boolean limit() default true;

}