package com.elegant.essay.service.impl;

import com.elegant.essay.enums.ErrorCodeEnum;
import com.elegant.essay.exception.BusinessException;
import com.elegant.essay.mapper.TransMsgStateRecordMapper;
import com.elegant.essay.model.TransMsgStateRecord;
import com.elegant.essay.service.ITransMsgStateRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-12-22 20:14
 */
@Service
public class TransMsgStateRecordServiceImpl implements ITransMsgStateRecordService {

    @Resource
    private TransMsgStateRecordMapper transMsgStateRecordMapper;

    @Override
    public void createTransMsg(TransMsgStateRecord msgStateRecord) {
        int result = transMsgStateRecordMapper.insertSelective(msgStateRecord);
        if (result <= 0) {
            throw new BusinessException(ErrorCodeEnum.TRANS_MSG_STATE_ADD_ERR.getCode(), ErrorCodeEnum.TRANS_MSG_STATE_ADD_ERR.getMsg());
        }
    }

    @Override
    public void updateTransMsg(TransMsgStateRecord msgStateRecord) {
        int result = transMsgStateRecordMapper.updateByPrimaryKeySelective(msgStateRecord);
        if (result <= 0) {
            throw new BusinessException(ErrorCodeEnum.TRANS_MSG_STATE_UPDATE_ERR.getCode(), ErrorCodeEnum.TRANS_MSG_STATE_UPDATE_ERR.getMsg());
        }
    }

    @Override
    public TransMsgStateRecord findMsgStateByTransId(String transId) {
        return transMsgStateRecordMapper.findRecordByTransId(transId);
    }

    @Override
    public TransMsgStateRecord findMsgStateByMsgKeys(String msgKeys) {
        return transMsgStateRecordMapper.findRecordByMsgKeys(msgKeys);
    }
}
