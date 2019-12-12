package com.elegant.essay.jms.repository;

import com.alibaba.fastjson.JSON;
import com.elegant.essay.model.ElegantEssay;
import com.elegant.essay.service.IEssayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


/**
 * @description: 消费卡夫卡的log日志
 * @author: xiaoxu.nie
 * @date: 2018-12-03 14:37
 */
@Slf4j
@Component
public class KafkaReceiverLogbackService {

    @KafkaListener(topics = {"Kafka-Logback-Logs"})
    public void esEssayListener(ConsumerRecord<?, ?> consumerRecord) {
        Optional<?> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent() && kafkaMessage.get() != null) {
            Object receiveMsg = kafkaMessage.get();
            log.info("Kafka 日志消费:{}", receiveMsg);
        }
    }
}
