package com.elegant.essay.service.impl;

import com.elegant.essay.enums.ErrorCodeEnum;
import com.elegant.essay.enums.EssayStatusEnum;
import com.elegant.essay.exception.BusinessException;
import com.elegant.essay.jms.config.MsgConfigProperties;
import com.elegant.essay.model.dto.EssayDto;
import com.elegant.essay.model.dto.ShippingAddressDto;
import com.elegant.essay.service.IEssayService;
import com.elegant.essay.service.IShippingAddressService;
import com.elegant.essay.service.ITransMsgService;
import io.shardingsphere.core.constant.transaction.TransactionType;
import io.shardingsphere.transaction.annotation.ShardingTransactionType;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-12-25 17:39
 */
@Slf4j
@Service
public class TransMsgServiceImpl implements ITransMsgService {

    @Autowired
    private TransactionMQProducer transactionMQProducer;

    @Autowired
    private MsgConfigProperties msgConfigProperties;

    @Autowired
    private IShippingAddressService shippingAddressService;

    @Autowired
    private IEssayService essayService;

    @Override
    public void producerTransMsg() {
        long timeStamp = System.currentTimeMillis();
        String tags = "ZM-MQ:" + timeStamp;
        String keys = "ZM-MQ-KEY:" + timeStamp;
        String msg = "ZM-TRANS-MSG:" + timeStamp;
        try {
            Message message = new Message(msgConfigProperties.getTransTopic(), tags, keys,
                    msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 向MQ发送消息
            SendResult sendResult = transactionMQProducer.sendMessageInTransaction(message, null);

            log.info("RocketMQ Send Msg Result:{}", sendResult);
        } catch (MQClientException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @ShardingTransactionType(TransactionType.XA)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void xaTransRollback(boolean isRollback) {
        // 测试目标：1.把两条数据指定到两个数据源中，测试回滚 2.把两条数据指定到一个数据源中测试回滚

        // 创建一个文章
        EssayDto essayDto = new EssayDto();
        essayDto.setUserId(1L);
        essayDto.setTitle("文章标题");
        essayDto.setContent("文章内容");
        essayDto.setSummary("文章摘要");
        essayDto.setSort(8);
        essayDto.setAllowComment(1);
        essayDto.setStatus(EssayStatusEnum.PUBLISHED.getCode());
        essayDto.setPublishTime(new Date());
        essayService.createEssay(essayDto);
        // 创建一个地址
        ShippingAddressDto addressDto = new ShippingAddressDto();
        addressDto.setUserId(2L);
        addressDto.setAddressDesc("金融街海伦中心");
        addressDto.setAreaId(8L);
        addressDto.setCellphone("16602116670");
        addressDto.setStreetId(9L);
        addressDto.setConsigneeName("聂晓旭");
        shippingAddressService.createAddress(addressDto);
        if (isRollback) {
            throw new BusinessException(ErrorCodeEnum.TRANS_ROLLBACK_ERR.getMsg());
        }

    }
}
