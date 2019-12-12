package com.elegant.essay.service.impl;

import com.elegant.essay.enums.ErrorCodeEnum;
import com.elegant.essay.enums.OrderItemStatusEnum;
import com.elegant.essay.enums.OrderStatusEnum;
import com.elegant.essay.enums.PayStatusEnum;
import com.elegant.essay.exception.BusinessException;
import com.elegant.essay.mapper.*;
import com.elegant.essay.model.*;
import com.elegant.essay.model.dto.CreateOrderDto;
import com.elegant.essay.model.dto.CreateOrderItemDto;
import com.elegant.essay.model.dto.GoodsOrderPageParam;
import com.elegant.essay.model.dto.UserQuery;
import com.elegant.essay.model.vo.GoodsOrderBriefVO;
import com.elegant.essay.model.vo.GoodsOrderDetailVO;
import com.elegant.essay.model.vo.GoodsOrderItemVO;
import com.elegant.essay.service.IGoodsOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import io.shardingsphere.api.HintManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-16 20:28
 */
@Service
public class GoodsOrderServiceImpl implements IGoodsOrderService {

    @Resource
    private ElegantUserMapper userMapper;

    @Resource
    private ArtisanGoodsSelfMapper goodsSelfMapper;

    @Resource
    private ShippingAddressMapper shippingAddressMapper;

    @Resource
    private GoodsOrderMapper goodsOrderMapper;

    @Resource
    private GoodsOrderSelfMapper goodsOrderSelfMapper;

    @Resource
    private GoodsOrderItemSelfMapper goodsOrderItemSelfMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void appCreateOrder(CreateOrderDto createOrderDto) {

        if (CollectionUtils.isEmpty(createOrderDto.getGoodsInfos())) {
            throw new BusinessException(ErrorCodeEnum.ORDER_GOODS_NULL_ERR.getCode(), ErrorCodeEnum.ORDER_GOODS_NULL_ERR.getMsg());
        }
        // 1. 查询用户
        ElegantUser elegantUser = userMapper.selectByPrimaryKey(createOrderDto.getUserId());
        if (elegantUser == null) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_EXIST_ERR.getCode(), ErrorCodeEnum.USER_NOT_EXIST_ERR.getMsg());
        }
        //2.查询地址
        ShippingAddress shippingAddress = shippingAddressMapper.selectByPrimaryKey(createOrderDto.getShippingAddressId());
        if (shippingAddress == null) {
            throw new BusinessException(ErrorCodeEnum.ADDRESS_NOT_EXISTS_ERR.getCode(), ErrorCodeEnum.ADDRESS_NOT_EXISTS_ERR.getMsg());
        }
        // 3.查询商品
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setUserId(elegantUser.getUserId());
        goodsOrder.setCellphone(shippingAddress.getCellphone());
        goodsOrder.setUserName(shippingAddress.getConsigneeName());
        goodsOrder.setShippingAddress(shippingAddress.getAddressDesc());
        goodsOrder.setCreateTime(new Date());
        goodsOrder.setOrderStatus(OrderStatusEnum.WAITING_DELIVERY.getCode());
        goodsOrder.setPayStatus(PayStatusEnum.NON_PAY.getCode());
        /*goodsOrder.setOrderId(dsAppBizUidService.genUniqueGoodsOrderId(BizTypeEnum.ORDER_ID.getCode(), BizTypeEnum.ORDER_ID.getCode()));*/
        goodsOrderMapper.insert(goodsOrder);
        // 批量查询用户下单商品；判断库存是否充足并减库存；计算总金额
        Map<Long, CreateOrderItemDto> goodsMap = createOrderDto.getGoodsInfos().stream().collect(Collectors.toMap(CreateOrderItemDto::getGoodsId, orderItemDto -> orderItemDto));
        List<Long> goodsIds = createOrderDto.getGoodsInfos().stream().map(CreateOrderItemDto::getGoodsId).collect(Collectors.toList());
        List<ArtisanGoods> artisanGoods = goodsSelfMapper.batchSelectGoodsByIds(goodsIds);
        // 初始化订单 TODO 检查库存
        List<GoodsOrderItem> orderItems = artisanGoods.stream().map(goods -> {
            GoodsOrderItem orderItem = new GoodsOrderItem();
            /*orderItem.setOrderItemId(dsAppBizUidService.genUniqueGoodsOrderId(BizTypeEnum.ORDER_ITEM_ID.getCode(), BizTypeEnum.ORDER_ITEM_ID.getCode()));*/
            orderItem.setGoodsIcon(goods.getGoodsIcon());
            orderItem.setGoodsId(goods.getGoodsId());
            orderItem.setGoodsName(goods.getGoodsName());
            orderItem.setPrice(goods.getPromotionPrice());
            orderItem.setQuantity(goodsMap.get(goods.getGoodsId()).getQuantity());
            orderItem.setTradeStatus(OrderItemStatusEnum.INIT.getCode());
            orderItem.setOrderId(goodsOrder.getOrderId());
            return orderItem;
        }).collect(Collectors.toList());
        // 批量插入订单项orderItems
        HintManager instance = HintManager.getInstance();
        // 基于hint分库并分表，Hint模式和列模式可以混用，使用前请先写配置文件
        instance.addDatabaseShardingValue("goods_order_item", goodsOrder.getUserId());
        /*instance.addTableShardingValue("goods_order_item",goodsOrder.getId());*/
        goodsOrderItemSelfMapper.batchInsertOrderItem(orderItems);
        instance.close();
        // 计算总价
        BigDecimal totalPrice = new BigDecimal(0);
        orderItems.stream().forEach(item -> totalPrice.add(item.getPrice().multiply(new BigDecimal(item.getQuantity()))));
        goodsOrder.setPrice(totalPrice);
        goodsOrder.setPayAmount(totalPrice);
        goodsOrderMapper.updateByPrimaryKey(goodsOrder);
        // TODO 扣库存
        List<ArtisanGoods> reduceGoods = Lists.newArrayList();
        artisanGoods.stream().forEach(dbGoods -> {
            CreateOrderItemDto orderItemDto = goodsMap.get(dbGoods.getGoodsId());
            if (dbGoods.getStocks() - orderItemDto.getQuantity() >= 0) {
                int stock = dbGoods.getStocks() - orderItemDto.getQuantity();
                dbGoods.setStocks(stock);
                reduceGoods.add(dbGoods);
            }
        });
        // 所有商品库存不足下单失败
        if (CollectionUtils.isEmpty(reduceGoods)) {
            throw new BusinessException(ErrorCodeEnum.GOODS_STOCKS_ERR.getCode(), ErrorCodeEnum.GOODS_STOCKS_ERR.getMsg());
        }
        // 批量更新商品库存
        goodsSelfMapper.batchUpdateGoodsStock(reduceGoods);
        Map<Long, ArtisanGoods> reduceGoodsMap = reduceGoods.stream().collect(Collectors.toMap(ArtisanGoods::getGoodsId, artisanGood -> artisanGood));
    }

    @Override
    public PageInfo<GoodsOrderBriefVO> orderPageQuery(GoodsOrderPageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<GoodsOrderBriefVO> orderBriefVOS = goodsOrderSelfMapper.findOrdersBriefByUserId(pageParam.getUserId());
        if (CollectionUtils.isEmpty(orderBriefVOS)) {
            return new PageInfo<>(orderBriefVOS);
        }
        PageInfo<GoodsOrderBriefVO> pageInfo = new PageInfo<>(orderBriefVOS);
        List<Long> orderIds = orderBriefVOS.stream().map(GoodsOrderBriefVO::getOrderId).collect(Collectors.toList());
        // 根据订单ID批量查询订单项，基于hint只分库
        HintManager instance = HintManager.getInstance();
        instance.addDatabaseShardingValue("goods_order_item", pageParam.getUserId());
        List<GoodsOrderItem> goodsOrderItems = goodsOrderItemSelfMapper.batchSelectOrderItemByOrderIds(orderIds);
        if (CollectionUtils.isEmpty(goodsOrderItems)) {
            throw new BusinessException(ErrorCodeEnum.ORDER_ITEM_NULL_ERR.getCode(), ErrorCodeEnum.ORDER_ITEM_NULL_ERR.getMsg());
        }
        Map<Long, List<GoodsOrderItem>> orderItemsMap = goodsOrderItems.stream().collect(Collectors.groupingBy(GoodsOrderItem::getOrderId));
        // 拼装订单项
        orderBriefVOS.stream().forEach(orderBriefVO -> {
            List<GoodsOrderItem> orderItems = orderItemsMap.get(orderBriefVO.getOrderId());
            if (CollectionUtils.isEmpty(orderItems)) {
                return;
            }
            orderBriefVO.setOrderItems(convertOrderItemToVo(orderItems));
        });
        pageInfo.setList(orderBriefVOS);
        return pageInfo;
    }

    @Override
    public GoodsOrderDetailVO queryOrderDetail(UserQuery userQuery) {

        GoodsOrderDetailVO orderDetailVO = goodsOrderSelfMapper.findOrderDetailById(userQuery);
        if (orderDetailVO == null) {
            throw new BusinessException(ErrorCodeEnum.ORDER_NOT_EXISTS_ERR.getCode(), ErrorCodeEnum.ORDER_NOT_EXISTS_ERR.getMsg());
        }
        // 根据订单ID批量查询订单项，基于hint只分库
        HintManager instance1 = HintManager.getInstance();
        instance1.addDatabaseShardingValue("goods_order_item", orderDetailVO.getUserId());
        List<GoodsOrderItem> orderItems = goodsOrderItemSelfMapper.findOrderItemsByOrderId(orderDetailVO.getOrderId());
        if (CollectionUtils.isEmpty(orderItems)) {
            return orderDetailVO;
        }
        orderDetailVO.setOrderItems(convertOrderItemToVo(orderItems));
        return orderDetailVO;
    }

    /**
     * 转换dbItem到itemVo
     *
     * @param orderItems
     * @return
     */
    private List<GoodsOrderItemVO> convertOrderItemToVo(List<GoodsOrderItem> orderItems) {
        if (CollectionUtils.isEmpty(orderItems)) {
            return Collections.emptyList();
        }
        return orderItems.stream().map(item -> {
            GoodsOrderItemVO orderItemVO = new GoodsOrderItemVO();
            orderItemVO.setOrderItemId(item.getOrderItemId());
            orderItemVO.setGoodsId(item.getGoodsId());
            orderItemVO.setGoodsName(item.getGoodsName());
            orderItemVO.setPrice(item.getPrice());
            orderItemVO.setQuantity(item.getQuantity());
            return orderItemVO;
        }).collect(Collectors.toList());
    }
}
