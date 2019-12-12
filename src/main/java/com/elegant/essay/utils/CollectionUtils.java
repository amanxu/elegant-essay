package com.elegant.essay.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-12-08 15:02
 */
public class CollectionUtils extends org.springframework.util.CollectionUtils {

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
}
