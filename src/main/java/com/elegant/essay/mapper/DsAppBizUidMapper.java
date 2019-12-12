package com.elegant.essay.mapper;

import com.elegant.essay.model.DsAppBizUid;
import org.apache.ibatis.annotations.Param;

public interface DsAppBizUidMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DsAppBizUid record);

    int insertSelective(DsAppBizUid record);

    DsAppBizUid selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DsAppBizUid record);

    int updateByPrimaryKey(DsAppBizUid record);

    DsAppBizUid findBizUidByAppAndBizName(@Param("appName") String appName, @Param("bizName") String bizName);
}