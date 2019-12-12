package com.elegant.essay.mapper;

import com.elegant.essay.model.ArtisanGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaoxu.nie
 */
public interface ArtisanGoodsSelfMapper {

    /**
     * 根据商品ID批量查询商品
     *
     * @param goodsIds
     * @return
     */
    List<ArtisanGoods> batchSelectGoodsByIds(@Param("goodsIds") List<Long> goodsIds);

    /**
     * 批量插入商品
     *
     * @param goodsList
     * @return
     */
    int batchInsertGoods(@Param("list") List<ArtisanGoods> goodsList);

    /**
     * 批量更新商品库存
     *
     * @param goodsList
     * @return
     */
    int batchUpdateGoodsStock(@Param("list") List<ArtisanGoods> goodsList);

}