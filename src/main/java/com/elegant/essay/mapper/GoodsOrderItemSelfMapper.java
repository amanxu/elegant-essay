package com.elegant.essay.mapper;

import com.elegant.essay.model.GoodsOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaoxu.nie
 */
public interface GoodsOrderItemSelfMapper {

    /**
     * 批量插入订单项
     * @param orderItems
     * @return
     */
    int batchInsertOrderItem(@Param("orderItems") List<GoodsOrderItem> orderItems);

    /**
     * 根据订单ID查询订单项
     *
     * @param orderId
     * @return
     */
    List<GoodsOrderItem> findOrderItemsByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据订单id集合查询所有订单项
     * @param orderIds
     * @return
     */
    List<GoodsOrderItem> batchSelectOrderItemByOrderIds(@Param("orderIds") List<Long> orderIds);
}