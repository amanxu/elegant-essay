package com.elegant.essay.controller;

import com.elegant.essay.model.dto.CreateOrderDto;
import com.elegant.essay.model.dto.GoodsOrderPageParam;
import com.elegant.essay.model.dto.IdQuery;
import com.elegant.essay.model.dto.UserQuery;
import com.elegant.essay.model.pojo.Result;
import com.elegant.essay.model.vo.GoodsOrderBriefVO;
import com.elegant.essay.model.vo.GoodsOrderDetailVO;
import com.elegant.essay.service.IGoodsOrderService;
import com.elegant.essay.utils.ResultUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-16 15:06
 */
@Api(description = "APP订单API")
@RestController
@RequestMapping(value = "/order")
public class AppGoodsOrderController {

    @Autowired
    private IGoodsOrderService goodsOrderService;

    @ApiOperation(value = "APP端创建订单")
    @PostMapping(value = "/appCreateOrder")
    public Result appCreateOrder(@RequestBody @Validated CreateOrderDto createOrderDto) {
        goodsOrderService.appCreateOrder(createOrderDto);
        return ResultUtil.success();
    }

    @ApiOperation(value = "订单分页查询")
    @PostMapping(value = "/orderPageQuery")
    public Result<PageInfo<GoodsOrderBriefVO>> orderPageQuery(@RequestBody @Validated GoodsOrderPageParam pageQuery) {
        return ResultUtil.success(goodsOrderService.orderPageQuery(pageQuery));
    }

    @ApiOperation(value = "订单详情")
    @PostMapping(value = "/orderDetail")
    public Result<GoodsOrderDetailVO> orderDetail(@RequestBody @Validated UserQuery userQuery) {

        return ResultUtil.success(goodsOrderService.queryOrderDetail(userQuery));
    }

    @ApiOperation(value = "删除订单")
    @PostMapping(value = "/orderDelete")
    public Result orderDelete(@RequestBody @Validated IdQuery idQuery) {

        return ResultUtil.success();
    }
}
