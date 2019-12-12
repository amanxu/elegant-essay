package com.elegant.essay.jms.transaction;

import com.elegant.essay.jms.config.MsgConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-12-23 21:57
 */
@Slf4j
@Configuration
public class TransMsgInitConfig {

    @Autowired
    private MsgConfigProperties msgConfigProperties;

    @Autowired
    private ConsumerTranMsgListener consumerTranMsgListener;

    @Autowired
    private TransactionListenerImpl transactionListener;

    /**
     * Spring中注册一个生产者，在需要发送事务消息的地方注入该Bean执行发送
     *
     * @return
     * @throws MQClientException
     */
    @Bean
    public TransactionMQProducer transactionMQProducer() throws MQClientException {

        TransactionMQProducer producer = new TransactionMQProducer(msgConfigProperties.getTransGroup());
        // 自定义线程池
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Client-Transaction-Msg-Check-Thread");
                return thread;
            }
        });
        producer.setExecutorService(executorService);
        // 本地事务执行和事务状态监听器
        producer.setTransactionListener(transactionListener);
        // RocketMq 服务地址
        producer.setNamesrvAddr(msgConfigProperties.getNameServer());
        return producer;
    }

    /**
     * Spring中注册一个消费者,设置监听器监听指定主题的消息
     *
     * @return
     * @throws MQClientException
     */
    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(msgConfigProperties.getTransGroup());
        consumer.setNamesrvAddr(msgConfigProperties.getNameServer());

        //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费；如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(msgConfigProperties.getTransTopic(), "*");

        consumer.registerMessageListener(consumerTranMsgListener);
        consumer.start();
        return consumer;
    }

}
