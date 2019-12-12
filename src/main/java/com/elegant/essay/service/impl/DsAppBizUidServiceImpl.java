package com.elegant.essay.service.impl;

import com.elegant.essay.enums.BizTypeEnum;
import com.elegant.essay.enums.ErrorCodeEnum;
import com.elegant.essay.exception.BusinessException;
import com.elegant.essay.mapper.DsAppBizUidMapper;
import com.elegant.essay.model.DsAppBizUid;
import com.elegant.essay.service.IDsAppBizUidService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 根据应用和业务类型动态分配数据库自增ID
 * @author: xiaoxu.nie
 * @date: 2018-11-20 14:54
 */
@Deprecated
@Slf4j
@Service
public class DsAppBizUidServiceImpl implements IDsAppBizUidService {

    private static String DS_APP_NAME = "elegant-essay";
    private static Queue<Long> goodsOrderBizUid = new LinkedList<>();
    private static Queue<Long> goodsOrderItemBizUid = new LinkedList<>();
    Lock orderIdLock = new ReentrantLock();
    private static long FIRST_LEVEL = 10000;
    private static long SEC_LEVEL = 20000;

    @Resource
    private DsAppBizUidMapper dsAppBizUidMapper;

    @Override
    public DsAppBizUid findBizUidByAppAndBizName(String appName, String bizName) {
        return dsAppBizUidMapper.findBizUidByAppAndBizName(appName, bizName);
    }

    @Override
    public Long genUniqueGoodsOrderId(String bizName, String bizType) {

        if (BizTypeEnum.ORDER_ID.getCode().endsWith(bizType)) {
            if (goodsOrderBizUid.isEmpty()) {
                // 动态分配uid
                dynamicAssignUid(bizName, bizType);
            }
            // 获取uid
            return goodsOrderBizUid.poll();
        } else if (BizTypeEnum.ORDER_ITEM_ID.getCode().endsWith(bizType)) {
            if (goodsOrderItemBizUid.isEmpty()) {
                // 动态分配uid
                dynamicAssignUid(bizName, bizType);
            }
            // 获取uid
            return goodsOrderItemBizUid.poll();
        }
        return null;
    }

    /**
     * 根据业务编码动态分配唯一主键
     *
     * @param bizName
     * @return
     */
    private void dynamicAssignUid(String bizName, String bizType) {

        orderIdLock.lock();
        try {
            DsAppBizUid dsAppBizUid = dsAppBizUidMapper.findBizUidByAppAndBizName(DS_APP_NAME, bizName);
            if (dsAppBizUid == null) {
                throw new BusinessException(ErrorCodeEnum.DS_APP_BIZ_UID_CONF_NULL_ERR.getMsg());
            }
            // 三级自动扩容，取值间隔按毫秒计算，200条ID(5000毫秒)，二级取500(2000毫秒)，三级取1000个(1000毫秒)
            long currentTime = System.currentTimeMillis();
            int assignCount = 0;
            if (currentTime - dsAppBizUid.getUpdateTime().getTime() < FIRST_LEVEL) {
                assignCount = 200;
            } else if (currentTime - dsAppBizUid.getUpdateTime().getTime() < SEC_LEVEL) {
                assignCount = 500;
            } else {
                assignCount = 1000;
            }
            if (BizTypeEnum.ORDER_ID.getCode().endsWith(bizType)) {
                for (long i = 1; i <= assignCount; i++) {
                    goodsOrderBizUid.add(dsAppBizUid.getBizUid() + i);
                }
            } else if (BizTypeEnum.ORDER_ITEM_ID.getCode().endsWith(bizType)) {
                for (long i = 1; i <= assignCount; i++) {
                    goodsOrderItemBizUid.add(dsAppBizUid.getBizUid() + i);
                }
            }
            dsAppBizUid.setBizUid(dsAppBizUid.getBizUid() + assignCount);
            dsAppBizUidMapper.updateByPrimaryKey(dsAppBizUid);
        } catch (Exception e) {
            log.error("Assign Uid Error:{}", e);
        } finally {
            orderIdLock.unlock();
        }
    }
}
