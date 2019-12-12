package com.elegant.essay.utils;

import com.elegant.essay.annotation.FieldCompare;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

/**
 * @description: 根据注解比较两个对象中指定字段是否相同
 * @author: xiaoxu.nie
 * @date: 2018-12-18 13:00
 */
@Slf4j
public class PojoInstanceCompareUtil {

    private static final String NULL = "null";

    private static final String CHANGE_DIR = "-->>";

    private PojoInstanceCompareUtil() {

    }

    /**
     * 比较两个需要的属性是否有变化，同时返回变化的属性字段和变化的值
     *
     * @param oldInstance
     * @param newInstance
     * @return
     */
    public static Map<String, String> compareObjInstances(Object oldInstance, Object newInstance) {

        Assert.notNull(oldInstance, "oldObj Is Null Error");
        Assert.notNull(newInstance, "newObj Is Null Error");

        Field[] fields = oldInstance.getClass().getDeclaredFields();
        Map<String, String> fieldMap = Maps.newHashMap();

        // 是否比较所有的class属性,如果类上标注了比较注解，则比较当前Class中的所有字段
        boolean isCompareAll = oldInstance.getClass().isAnnotationPresent(FieldCompare.class);

        Arrays.stream(fields).forEach(field -> {
            // 设置私有属性可以访问
            field.setAccessible(true);
            // 判断当前field是否添加比较注解
            boolean annotationPresent = field.isAnnotationPresent(FieldCompare.class);
            try {
                Object oldValue = field.get(oldInstance);
                Object newValue = field.get(newInstance);
                if (isCompareAll) {
                    compareFieldValue(fieldMap, oldValue, newValue, field.getName());
                    return;
                }
                // 判断field中是否添加比较注解,较注解的属性值是否为true
                if (annotationPresent && field.getAnnotation(FieldCompare.class).isCompare()) {
                    compareFieldValue(fieldMap, oldValue, newValue, field.getName());
                }
            } catch (IllegalAccessException e) {
                log.error("PojoInstanceCompareUtil IllegalAccessException:{}", e);
            }
        });
        return fieldMap;
    }

    /**
     * 比较当前field具体值
     *
     * @param fieldMap
     * @param oldValue
     * @param newValue
     */
    private static void compareFieldValue(Map<String, String> fieldMap, Object oldValue, Object newValue, String fieldName) {
        StringBuilder builder = new StringBuilder();
        if (ObjectUtils.isEmpty(oldValue) && ObjectUtils.isEmpty(newValue)) {
            log.debug("OldValue and new Value are empty");
        } else if (ObjectUtils.isEmpty(oldValue) && ObjectUtils.isNotEmpty(newValue)) {
            builder.append(NULL).append(CHANGE_DIR).append(newValue.toString());
            fieldMap.put(fieldName, builder.toString());
        } else if (ObjectUtils.isNotEmpty(oldValue) && ObjectUtils.isEmpty(newValue)) {
            builder.append(oldValue.toString()).append(CHANGE_DIR).append(NULL);
            fieldMap.put(fieldName, builder.toString());
        } else if (ObjectUtils.isNotEmpty(oldValue) && ObjectUtils.isNotEmpty(newValue)
                && !oldValue.toString().equals(newValue.toString())) {
            builder.append(oldValue.toString()).append(CHANGE_DIR).append(newValue.toString());
            fieldMap.put(fieldName, builder.toString());
        }
    }

    /**
     * 判断需要比较的属性是否有变化
     *
     * @param oldInstance
     * @param newInstance
     * @return 有变化返回true
     */
    public static boolean compareInstances(Object oldInstance, Object newInstance) {
        Map<String, String> compareMap = compareObjInstances(oldInstance, newInstance);

        return !CollectionUtils.isEmpty(compareMap);
    }

}
