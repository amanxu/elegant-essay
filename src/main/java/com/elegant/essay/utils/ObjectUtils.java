package com.elegant.essay.utils;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-12-19 14:16
 */

public class ObjectUtils extends org.springframework.util.ObjectUtils {
    public ObjectUtils() {
    }

    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
}