package com.elegant.essay.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-09-25 22:42
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssConfigProperties {

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    private String endPoint;

    private String ossFilePath;
}
