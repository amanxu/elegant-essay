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
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-12-03 14:37
 */
@Slf4j
@Component
public class KafkaReceiverService {

    @Autowired
    private IEssayService essayService;

    @KafkaListener(topics = {"ESSAY_ES_TOPIC"})
    public void esEssayListener(ConsumerRecord<?, ?> consumerRecord) {
        Optional<?> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent() && kafkaMessage.get() != null) {
            Object receiveMsg = kafkaMessage.get();
            List<ElegantEssay> essays = JSON.parseArray(receiveMsg.toString(), ElegantEssay.class);
            log.info("KAFKA ESSAY:{}", JSON.toJSONString(essays));
            essayService.bulkIndex(essays);
        }
    }
}
