package com.elegant.essay.mapper;

import com.elegant.essay.model.TransMsgConsumeRecord;
import org.apache.ibatis.annotations.Param;

public interface TransMsgConsumeRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TransMsgConsumeRecord record);

    int insertSelective(TransMsgConsumeRecord record);

    TransMsgConsumeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TransMsgConsumeRecord record);

    int updateByPrimaryKey(TransMsgConsumeRecord record);

    /**
     * 根据事务ID查询事务消息消费记录
     *
     * @param transId
     * @return
     */
    TransMsgConsumeRecord findTransMsgRecordByTransId(@Param("transId") String transId);

    /**
     * 根据消息ID查询事务消息消费记录
     *
     * @param msgId
     * @return
     */
    TransMsgConsumeRecord findTransMsgRecordByMsgId(@Param("msgId") String msgId);
}