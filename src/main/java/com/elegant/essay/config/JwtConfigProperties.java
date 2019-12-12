package com.elegant.essay.config;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-08 10:16
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigProperties {

    private String base64Secret;
    private long expiresTime;
    /**
     * 静态资源路径
     */
    private String staticPath;

    public List<String> unLimitPath() {
        if (StringUtils.isBlank(this.staticPath)) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(staticPath.trim().split(","));
    }
}
