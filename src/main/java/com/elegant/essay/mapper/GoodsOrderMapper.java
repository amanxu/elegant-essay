package com.elegant.essay.mapper;

import com.elegant.essay.model.GoodsOrder;

public interface GoodsOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(GoodsOrder record);

    int insertSelective(GoodsOrder record);

    GoodsOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(GoodsOrder record);

    int updateByPrimaryKey(GoodsOrder record);
}