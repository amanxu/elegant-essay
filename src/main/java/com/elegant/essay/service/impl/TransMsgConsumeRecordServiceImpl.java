package com.elegant.essay.service.impl;

import com.elegant.essay.enums.ErrorCodeEnum;
import com.elegant.essay.exception.BusinessException;
import com.elegant.essay.mapper.TransMsgConsumeRecordMapper;
import com.elegant.essay.model.TransMsgConsumeRecord;
import com.elegant.essay.service.ITransMsgConsumeRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-12-23 22:40
 */
@Service
public class TransMsgConsumeRecordServiceImpl implements ITransMsgConsumeRecordService {

    @Resource
    private TransMsgConsumeRecordMapper transMsgConsumeRecordMapper;

    @Override
    public void createMsgRecord(TransMsgConsumeRecord consumeRecord) {
        int result = transMsgConsumeRecordMapper.insertSelective(consumeRecord);
        if (result <= 0) {
            throw new BusinessException(ErrorCodeEnum.TRANS_MSG_CONSUME_LOG_ADD_ERR.getMsg());
        }
    }

    @Override
    public void updateMsgRecord(TransMsgConsumeRecord consumeRecord) {
        int result = transMsgConsumeRecordMapper.updateByPrimaryKeySelective(consumeRecord);
        if (result <= 0) {
            throw new BusinessException(ErrorCodeEnum.TRANS_MSG_CONSUME_LOG_ADD_ERR.getMsg());
        }
    }

    @Override
    public TransMsgConsumeRecord findMsgRecordByTransId(String transId) {
        return transMsgConsumeRecordMapper.findTransMsgRecordByTransId(transId);
    }

    @Override
    public TransMsgConsumeRecord findMsgRecordByMsgId(String msgId) {
        return transMsgConsumeRecordMapper.findTransMsgRecordByMsgId(msgId);
    }
}
