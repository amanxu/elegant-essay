package com.elegant.essay.jms.repository;

import com.alibaba.fastjson.JSON;
import com.elegant.essay.model.ElegantEssay;
import com.elegant.essay.service.IEssayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-12-04 14:54
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "${spring.rocketmq.essayTopic}", consumerGroup = "essay-consumer-group")
public class RocketEssayConsumer implements RocketMQListener<String> {

    @Autowired
    private IEssayService essayService;

    @Override
    public void onMessage(String message) {
        log.info("Essay Rocket Consumer Received: {}", message);
        if (StringUtils.isNotBlank(message)) {
            essayService.bulkIndex(JSON.parseArray(message, ElegantEssay.class));
        }
    }
}