package com.elegant.essay.mapper;

import com.elegant.essay.model.ArtisanGoods;

public interface ArtisanGoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(ArtisanGoods record);

    int insertSelective(ArtisanGoods record);

    ArtisanGoods selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(ArtisanGoods record);

    int updateByPrimaryKey(ArtisanGoods record);
}