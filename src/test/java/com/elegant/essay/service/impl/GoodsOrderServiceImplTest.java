package com.elegant.essay.service.impl;

import com.alibaba.fastjson.JSON;
import com.elegant.essay.model.dto.CreateOrderDto;
import com.elegant.essay.model.dto.CreateOrderItemDto;
import com.elegant.essay.service.IGoodsOrderService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-19 15:05
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsOrderServiceImplTest {

    @Autowired
    private IGoodsOrderService goodsOrderService;

    @Test
    public void appCreateOrder() throws Exception {
        Random random = new Random();
        List<CreateOrderItemDto> goodsInfo = Lists.newArrayList();
        CreateOrderItemDto orderItemDto1 = new CreateOrderItemDto(1L, 1);
        goodsInfo.add(orderItemDto1);
        CreateOrderItemDto orderItemDto2 = new CreateOrderItemDto(2L, 2);
        goodsInfo.add(orderItemDto2);
        CreateOrderItemDto orderItemDto3 = new CreateOrderItemDto(3L, 5);
        goodsInfo.add(orderItemDto3);
        CreateOrderItemDto orderItemDto4 = new CreateOrderItemDto(4L, 1);
        goodsInfo.add(orderItemDto4);

        for (int i = 0; i < 20000; i++) {
            Long paramId = Long.parseLong(String.valueOf(random.nextInt(4) + 1));
            CreateOrderDto createOrderDto = new CreateOrderDto();
            createOrderDto.setUserId(paramId);
            createOrderDto.setShippingAddressId(paramId);
            createOrderDto.setGoodsInfos(goodsInfo);
            goodsOrderService.appCreateOrder(createOrderDto);
            Thread.sleep(1);
            log.info("构造参数：{}，{}", i, JSON.toJSONString(createOrderDto));
        }
    }

}