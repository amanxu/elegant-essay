package com.elegant.essay.service;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-12-25 17:38
 */
public interface ITransMsgService {

    /**
     * 生产者生成事务消息
     */
    void producerTransMsg();

    /**
     * xa强一致事务回滚测试
     * @param isRollback
     */
    void xaTransRollback(boolean isRollback);
}
