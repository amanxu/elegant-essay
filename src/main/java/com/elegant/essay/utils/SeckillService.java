package com.elegant.essay.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @description: 悲观锁实现
 * @author: niexx <br>
 * @date: 2018-06-08 21:32 <br>
 */
@Slf4j
@Service
public class SeckillService {

    private static String lockName = "PRO-NUM";

    @Autowired
    private RedisTemplate redisTemplate;

    /***
     * 抢购代码
     * @param key pro num 首先用客户端设置数量
     * @return
     */
    public boolean seckill(String key) {
        // 锁的过期时间
        long expireTime = System.currentTimeMillis() + 5000;
        RedisLock lock = new RedisLock(redisTemplate, key, 5000);
        try {
            // 尝试获取redis锁
            if (lock.tryLock()) {
                String proNum = lock.get(lockName);
                // TODO 修改库存
                if (Integer.parseInt(proNum) - 1 >= 0) {
                    lock.set(lockName, String.valueOf(Integer.parseInt(proNum) - 1));
                    log.info("剩余库存数量:" + proNum + "  抢购成功! 抢购者：" + Thread.currentThread().getName());
                    return true;
                }
                log.info("库存不足：{}", Thread.currentThread().getName());
            }
            log.info("用户：{} 没有抢购到商品", Thread.currentThread().getName());
        } catch (Exception e) {
            log.error("SeckillService seckill Exception:{}", e);
        } finally {
            lock.unlock();
        }
        return false;
    }
}
