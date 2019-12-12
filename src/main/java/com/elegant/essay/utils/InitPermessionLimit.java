package com.elegant.essay.utils;

import com.elegant.essay.annotation.PermessionLimit;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @description: 获取controller中PermessionLimit注解的方法的路径(controller)上必须加注解RequestMapping
 * @author: xiaoxu.nie
 * @date: 2018-12-08 15:19
 */
@Component
public class InitPermessionLimit implements ApplicationListener<ContextRefreshedEvent> {

    public static List<String> limitUrl = Lists.newArrayList();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, Object> beansWithAnnotation = Maps.newHashMap();
        // 获取Controller注解的Bean
        Map<String, Object> controllerBeanMap = event.getApplicationContext().getBeansWithAnnotation(Controller.class);
        if (CollectionUtils.isNotEmpty(controllerBeanMap)) {
            beansWithAnnotation.putAll(controllerBeanMap);
        }
        // 获取RestController注解的Bean
        Map<String, Object> restControllerBeanMap = event.getApplicationContext().getBeansWithAnnotation(RestController.class);
        if (CollectionUtils.isNotEmpty(restControllerBeanMap)) {
            beansWithAnnotation.putAll(restControllerBeanMap);
        }

        beansWithAnnotation.values().stream().forEach(bean -> {
            // 需要过滤登陆权限的URL
            List<String> filterUrls = Lists.newArrayList();

            // 如果class上加了PermessionLimit注解，则忽略当前类下所有URL
            boolean annotationPresent = bean.getClass().isAnnotationPresent(PermessionLimit.class);

            // 获取bean中RequestMapping注解的数组值
            String[] classUrls = null;
            RequestMapping annotation = bean.getClass().getAnnotation(RequestMapping.class);
            if (annotation != null) {
                classUrls = annotation.value();
            }
            Method[] methods = bean.getClass().getMethods();
            // 如果Class上有鉴权注解则类下的所有方法都无需鉴权
            if (annotationPresent) {
                filterUrls = parseMethodUrlWithClassByAnnotation(classUrls, methods, true);
            } else {
                filterUrls = parseMethodUrlWithClassByAnnotation(classUrls, methods, false);
            }

            if (CollectionUtils.isNotEmpty(filterUrls)) {
                limitUrl.addAll(filterUrls);
            }
        });
    }

    /**
     * 解析方法上的URL，同时拼接类上的URL
     *
     * @param classUrls
     * @param methods
     * @param isAllMethod 是否解析class下的所有方法,如果为false则解析添加鉴权注解的方法
     * @return
     */
    private List<String> parseMethodUrlWithClassByAnnotation(String[] classUrls, Method[] methods, boolean isAllMethod) {

        List<String> filterUrls = Lists.newArrayList();
        //1. 如果类上的URL为空则只解析方法上的URL
        if (ArrayUtils.isEmpty(classUrls)) {
            List<String> methodUrls = parseMethodUrlUnderClassByAnnotation(methods);
            if (CollectionUtils.isNotEmpty(methodUrls)) {
                filterUrls.addAll(methodUrls);
            }
            return filterUrls;
        }

        if (ArrayUtils.isEmpty(methods)) {
            return Collections.emptyList();
        }
        //2. 如果类上的URL不是空的，则解析并拼接URL
        Arrays.stream(classUrls).forEach(classUrl -> {
            Arrays.stream(methods).forEach(method -> {
                StringBuilder buf = new StringBuilder(classUrl);
                String[] methodUrls = null;
                if (isAllMethod) {
                    methodUrls = parseMethodMapping(method);
                } else {
                    methodUrls = parseMethodUrlByAnnotation(method);
                }
                if (ArrayUtils.isEmpty(methodUrls)) {
                    return;
                }
                // 拼接Class上的URL和方法上的URL
                Arrays.stream(methodUrls).forEach(methodUrl -> {
                    filterUrls.add(buf.append(methodUrl).toString());
                });
            });
        });
        return filterUrls;
    }


    /**
     * 解析class下方法上的注解及URL
     *
     * @param methods
     * @return
     */
    private List<String> parseMethodUrlUnderClassByAnnotation(Method[] methods) {
        List<String> methodUrls = Lists.newArrayList();
        Arrays.stream(methods).forEach(method -> {
            String[] methodMapUrls = parseMethodUrlByAnnotation(method);
            if (ArrayUtils.isNotEmpty(methodMapUrls)) {
                methodUrls.addAll(Lists.newArrayList(methodMapUrls));
            }
        });
        return methodUrls;
    }

    /**
     * 解析方法上是否添加鉴权注解
     *
     * @param method
     * @return
     */
    private String[] parseMethodUrlByAnnotation(Method method) {
        if (method == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        // 如果方法上不包含健全注解则不解析
        if (method.isAnnotationPresent(PermessionLimit.class)) {
            return parseMethodMapping(method);
        }
        return ArrayUtils.EMPTY_STRING_ARRAY;
    }

    /**
     * 解析方法上的URL
     *
     * @param method
     * @return
     */
    private String[] parseMethodMapping(Method method) {
        String[] methodMapUrls = null;
        if (method.isAnnotationPresent(PostMapping.class)) {
            methodMapUrls = method.getAnnotation(PostMapping.class).value();
        } else if (method.isAnnotationPresent(GetMapping.class)) {
            methodMapUrls = method.getAnnotation(GetMapping.class).value();
        } else if (method.isAnnotationPresent(RequestMapping.class)) {
            methodMapUrls = method.getAnnotation(RequestMapping.class).value();
        }
        return methodMapUrls;
    }
}
