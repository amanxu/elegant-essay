package com.elegant.essay.jms.transaction;

import com.elegant.essay.enums.ConsumeTranStepEnum;
import com.elegant.essay.model.TransMsgConsumeRecord;
import com.elegant.essay.service.ITransMsgConsumeRecordService;
import com.elegant.essay.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-12-23 22:00
 */
@Slf4j
@Service
public class ConsumerTranMsgListener implements MessageListenerOrderly {

    @Autowired
    private ITransMsgConsumeRecordService transMsgConsumeRecordService;

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        context.setAutoCommit(true);

        TransMsgConsumeRecord consumeRecord = null;
        try {
            // 消费端接收订阅的消息并消费
            for (MessageExt msg : msgs) {
                // TODO 是否需要Redis锁定当前消息
                log.info("Customer Received Msg TranId:{}; Content: {}; ", msg.getTransactionId(), new String(msg.getBody()));
                // 获取消息ID，根据事务ID查询当前消息是否被消费过，避免重复消费
                TransMsgConsumeRecord msgRecord = transMsgConsumeRecordService.findMsgRecordByMsgId(msg.getMsgId());
                // 判断事务消费日志是否存在，同时判断事务消息的消费状态，如果消费成功则丢弃当前消息
                if (ObjectUtils.isNotEmpty(msgRecord) && ConsumeTranStepEnum.SUCCESS.getCode().equals(msgRecord.getTransState())) {
                    return ConsumeOrderlyStatus.SUCCESS;
                }
                // 解析当前消息
                consumeRecord = convertMsgToRecord(msg);
                transMsgConsumeRecordService.createMsgRecord(consumeRecord);

                //TODO 消费端模拟业务处理

                // 本地事务执行成功，更新事务消息消费结果
                consumeRecord.setTransState(ConsumeTranStepEnum.SUCCESS.getCode());
                transMsgConsumeRecordService.updateMsgRecord(consumeRecord);
            }
        } catch (Exception e) {
            log.error("{} Exception:{}", Thread.currentThread().getName(), e);
            consumeRecord.setTransState(ConsumeTranStepEnum.SUSPEND_CURRENT_QUEUE_A_MOMENT.getCode());
            transMsgConsumeRecordService.updateMsgRecord(consumeRecord);
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
        return ConsumeOrderlyStatus.SUCCESS;
    }

    /**
     * 转换事务消息为事务消息日志
     *
     * @param msg
     * @return
     */
    private static TransMsgConsumeRecord convertMsgToRecord(MessageExt msg) {
        TransMsgConsumeRecord consumeRecord = new TransMsgConsumeRecord();
        consumeRecord.setTransId(msg.getTransactionId());
        consumeRecord.setMsgBody(new String(msg.getBody()));
        consumeRecord.setMsgId(msg.getMsgId());
        consumeRecord.setQueueId(msg.getQueueId());
        consumeRecord.setBornTime(new Date(msg.getBornTimestamp()));
        consumeRecord.setReconsumeTimes(msg.getReconsumeTimes());
        consumeRecord.setStoreTime(new Date(msg.getStoreTimestamp()));
        consumeRecord.setSysFlag(msg.getSysFlag());
        consumeRecord.setFlag(msg.getFlag());
        consumeRecord.setTopic(msg.getTopic());
        consumeRecord.setMsgKeys(msg.getKeys());

        consumeRecord.setMsgUniqKey(msg.getProperty(MessageConst.PROPERTY_UNIQ_CLIENT_MESSAGE_ID_KEYIDX));
        consumeRecord.setMsgTags(msg.getProperty(MessageConst.PROPERTY_TAGS));
        consumeRecord.setMsgGroup(msg.getProperty(MessageConst.PROPERTY_PRODUCER_GROUP));

        String tranMsg = msg.getProperty(MessageConst.PROPERTY_TRANSACTION_PREPARED);
        consumeRecord.setMsgIsTran(StringUtils.isBlank(tranMsg) ? false : Boolean.parseBoolean(tranMsg));

        String msgWait = msg.getProperty(MessageConst.PROPERTY_WAIT_STORE_MSG_OK);
        consumeRecord.setMsgIsWait(StringUtils.isBlank(msgWait) ? false : Boolean.parseBoolean(msgWait));

        String realQueueId = msg.getProperty(MessageConst.PROPERTY_REAL_QUEUE_ID);
        consumeRecord.setMsgRealQueueId(StringUtils.isBlank(realQueueId) ? null : Integer.parseInt(realQueueId));

        String transCheckTimes = msg.getProperty(MessageConst.PROPERTY_TRANSACTION_CHECK_TIMES);
        consumeRecord.setMsgTranCheckTimes(StringUtils.isBlank(transCheckTimes) ? null : Integer.parseInt(transCheckTimes));
        consumeRecord.setTransState(ConsumeTranStepEnum.NOT_CONSUME.getCode());
        consumeRecord.setCreateTime(new Date());
        return consumeRecord;
    }

}
