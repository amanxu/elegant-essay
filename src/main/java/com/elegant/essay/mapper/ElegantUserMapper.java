package com.elegant.essay.mapper;

import com.elegant.essay.model.ElegantUser;

public interface ElegantUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(ElegantUser record);

    int insertSelective(ElegantUser record);

    ElegantUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(ElegantUser record);

    int updateByPrimaryKey(ElegantUser record);
}