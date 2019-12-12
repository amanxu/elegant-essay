package com.elegant.essay.service;

import com.elegant.essay.model.dto.CreateOrderDto;
import com.elegant.essay.model.dto.GoodsOrderPageParam;
import com.elegant.essay.model.dto.IdQuery;
import com.elegant.essay.model.dto.UserQuery;
import com.elegant.essay.model.vo.GoodsOrderBriefVO;
import com.elegant.essay.model.vo.GoodsOrderDetailVO;
import com.github.pagehelper.PageInfo;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-16 19:58
 */
public interface IGoodsOrderService {

    /**
     * APP创建订单请求
     *
     * @param createOrderDto
     */
    void appCreateOrder(CreateOrderDto createOrderDto);

    /**
     * 订单检索
     *
     * @param pageParam
     * @return
     */
    PageInfo<GoodsOrderBriefVO> orderPageQuery(GoodsOrderPageParam pageParam);

    /**
     * 订单详情
     *
     * @param userQuery
     * @return
     */
    GoodsOrderDetailVO queryOrderDetail(UserQuery userQuery);

}
