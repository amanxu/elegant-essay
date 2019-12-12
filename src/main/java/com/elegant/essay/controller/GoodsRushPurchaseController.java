package com.elegant.essay.controller;

import com.elegant.essay.annotation.PermessionLimit;
import com.elegant.essay.model.pojo.Result;
import com.elegant.essay.utils.ResultUtil;
import com.elegant.essay.utils.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-12-26 11:49
 */
@Slf4j
@Api(description = "抢购服务API")
@PermessionLimit(limit = false)
@RestController
@RequestMapping("/rushPurchase")
public class GoodsRushPurchaseController {

    private ExecutorService cachedThreadPool;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillService seckillService;

    @ApiOperation(value = "商品抢购")
    @GetMapping("/hotGoods")
    public Result rushPurchaseGoods(@RequestParam("userNum") Integer userNum, @RequestParam("proNum") Integer proNum) {
        redisTemplate.opsForValue().set("PRO-NUM", proNum);
        // 初始化线程池
        cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < userNum; i++) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    seckillService.seckill("SECKILL");
                }
            });
        }
        return ResultUtil.success();
    }
}
