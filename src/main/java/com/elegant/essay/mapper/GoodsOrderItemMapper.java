package com.elegant.essay.mapper;

import com.elegant.essay.model.GoodsOrderItem;

public interface GoodsOrderItemMapper {
    int deleteByPrimaryKey(Long orderItemId);

    int insert(GoodsOrderItem record);

    int insertSelective(GoodsOrderItem record);

    GoodsOrderItem selectByPrimaryKey(Long orderItemId);

    int updateByPrimaryKeySelective(GoodsOrderItem record);

    int updateByPrimaryKey(GoodsOrderItem record);
}