package com.elegant.essay.service;

import com.elegant.essay.model.DsAppBizUid;

/**
 * @description: 根据应用和业务类型动态分配数据库自增ID
 * @author: xiaoxu.nie
 * @date: 2018-11-20 14:32
 */
@Deprecated
public interface IDsAppBizUidService {

    /**
     * 根据应用名称和业务名称查询主键
     *
     * @param appName
     * @param bizName
     * @return
     */
    DsAppBizUid findBizUidByAppAndBizName(String appName, String bizName);

    /**
     * 根据业务代码获取UID
     *
     * @return
     */
    Long genUniqueGoodsOrderId(String bizName, String bizType);

}
