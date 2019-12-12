package com.elegant.essay.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Random;

/**
 * @description:
 * @author: niexx <br>
 * @date: 2018-06-08 16:28 <br>
 */
@Slf4j
public class RedisLock {

    private RedisTemplate<String, Object> redisTemplate;

    private String lockKey;

    /*** 锁超时时间，防止线程在入锁以后，无限的执行等待*/
    private int expireMsec = 10 * 1000;

    /*** 锁的过期时间点*/
    private long lockExpireTimeStamp;

    /*** 默认尝试获取锁的次数*/
    private int tryTimes = 5;

    /***连续两次尝试获取锁的间隔时间边界值*/
    private int intervalTime = 100;

    /***基础边界值，防止随机边界产生过小的值*/
    private int baseBound = 60;

    private volatile boolean locked = false;

    private Random random;

    /**
     * constructor  default acquire timeout 10000 msec and lock expiration of 60000 msec
     *
     * @param redisTemplate
     * @param lockKey
     */
    public RedisLock(RedisTemplate<String, Object> redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey;
        this.random = new Random();
    }


    public RedisLock(RedisTemplate<String, Object> redisTemplate, String lockKey, int expireMsec) {
        this(redisTemplate, lockKey);
        this.expireMsec = expireMsec;
    }

    /**
     * @return lock key
     */
    public String getLockKey() {
        return lockKey;
    }

    public String get(final String key) {
        Object obj = null;
        try {
            obj = redisTemplate.opsForValue().get(key);
        } catch (Exception ex) {
            log.error("RedisLock Get Redis Error; KEY:{},ERR:{}", key, ex);
        }
        return obj == null ? null : obj.toString();
    }


    public void set(final String key, final String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception ex) {
            log.error("RedisLock Set Redis Error; KEY:{}ERR:{}", key, ex);
        }
    }

    public Boolean setNX(final String key, final String value) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
            log.error("RedisLock SetNX Redis Error, KEY : {},ERR:{}", key, e);
        }
        return false;
    }

    private String getSet(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.opsForValue().getAndSet(key, value);
        } catch (Exception e) {
            log.error("RedisLock GetSetNX Redis Error, KEY:{},ERR:{}", key, e);
        }
        return obj == null ? null : obj.toString();
    }

    /**
     * 尝试获取Redis锁，默认尝试3次，边界间隔时间为100毫秒
     *
     * @return
     */
    public synchronized boolean tryLock() {
        while (tryTimes > 0) {
            if (lock()) {
                locked = true;
                return locked;
            }
            --tryTimes;
            try {
                // 使用随机时间,可以防止饥饿进程的出现,即当同时到达多个进程,只会有一个进程获得锁,使用随机的等待时间可以一定程度上保证公平性
                Thread.sleep(baseBound + random.nextInt(intervalTime));
            } catch (InterruptedException e) {
                log.error("RedisLock TryLock InterruptedException:{}", e);
            }
        }
        return false;
    }

    /**
     * 间隔指定时间（duringTime）尝试tryTimes次获取锁
     *
     * @param tryTimes
     * @param intervalTime 毫秒 随机时间边界值
     * @return
     */
    public synchronized boolean tryLock(int tryTimes, int intervalTime) {
        this.tryTimes = tryTimes;
        this.intervalTime = intervalTime;
        return tryLock();
    }

    /**
     * 获得 lock. 实现思路: 主要是使用了redis 的setnx命令,缓存了锁. reids缓存的key是锁的key,所有的共享,
     * value是锁的到期时间(注意:这里把过期时间放在value了,没有时间上设置其超时时间) 执行过程:
     * 1.通过setNx尝试设置某个key的值,成功(当前没有这个锁)则返回,成功获得锁
     * 2.锁已经存在则获取锁的到期时间,和当前时间比较,超时的话,则设置新的值
     *
     * @return true if lock is acquired, false acquire timeout
     */
    private boolean lock() {

        lockExpireTimeStamp = System.currentTimeMillis() + expireMsec;
        // 锁的到期时间
        String expiresStr = String.valueOf(lockExpireTimeStamp);
        // 1.判断redis中是否存在锁对应的key,如果没有则设置key和值
        if (this.setNX(lockKey, expiresStr)) {
            // 获取到锁
            locked = true;
            return locked;
        }

        String oldExpiresValue = this.get(lockKey);
        // 2.判断是否为空，不为空的情况下判断锁是否到期，到期则删除原有锁的到期时间，设置新的时间
        if (StringUtils.isNotBlank(oldExpiresValue) && Long.parseLong(oldExpiresValue) < System.currentTimeMillis()) {
            // 获取上一个锁到期时间，并设置现在的锁到期时间，只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
            String getSetExpiresValue = this.getSet(lockKey, expiresStr);
            //3. 防止误删（覆盖，因为key是相同的）了他人的锁，此处值可能会被覆盖，但是因为相差了很少的时间，基本可以接受
            // 分布式下如果此时多个线程恰好都到了这里，但是只有一个线程的getSet方法返回的值和旧的过期时间相同，它才有权利获取到锁
            if (StringUtils.isNotBlank(getSetExpiresValue) && getSetExpiresValue.equals(oldExpiresValue)) {
                locked = true;
                return locked;
            }
        }
        return false;
    }

    /**
     * 释放锁
     */
    public synchronized void unlock() {
        if (locked) {
            // 检查自己的锁是否已经超时，再去做DEL操作,因为可能某个耗时的操作当前线程挂起，操作完的时锁已经超时被别人获得，此时不必解锁
            if (System.currentTimeMillis() < lockExpireTimeStamp) {
                redisTemplate.delete(lockKey);
                locked = false;
            }
        }
    }

}
