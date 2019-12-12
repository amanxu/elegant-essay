package com.elegant.essay.jms.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-12-22 12:57
 */
@Slf4j
@Service
public class TransactionConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");
        consumer.setNamesrvAddr("127.0.0.1:9876");

        //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费；如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("Topic_Tran", "*");

        consumer.registerMessageListener(new MessageListenerOrderly() {

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
                try {
                    // 消费端接收订阅的消息并消费
                    for (MessageExt msg : msgs) {
                        // 注：TODO 0. 是否需要锁定当前消息
                        log.info("Customer Received Msg TranId:{}; Content: {}; ", msg.getTransactionId(), new String(msg.getBody()));
                        // TODO 1.获取消息ID，查询数据库检测当前消息是否被消费过，避免重复消费
                        // TODO 2.如果没有被消费过，解析当前消息并持久化到数据库
                        // TODO 3.开始消费当前消息，执行本地业务和事务
                    }
                } catch (Exception e) {
                    log.error("{} Exception:{}", Thread.currentThread().getName(), e);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        log.info("Consumer started !!! ");
    }
}
