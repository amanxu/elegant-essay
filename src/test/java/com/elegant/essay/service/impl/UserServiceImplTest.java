package com.elegant.essay.service.impl;

import com.elegant.essay.model.dto.UserDto;
import com.elegant.essay.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2018-11-22 20:03
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    @Test
    public void appCreateOrder() throws Exception {
        for (int i = 0; i < 1000; i++) {
            long timeStamp = System.currentTimeMillis();
            UserDto userDto = new UserDto();
            userDto.setUserName("amanxu_" + i);
            userDto.setEmail("amanxu_" + i + "_@126.com");
            userDto.setPassword("123456_" + i);
            userDto.setRealName("xu.nie_" + i);
            String value = String.valueOf(System.currentTimeMillis() - new Random().nextInt(100000));
            userDto.setPhone(value.substring(value.length() - 11));
            userDto.setUserType(0);
            userService.create(userDto);
            Thread.sleep(1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // StopWatch的使用
        StopWatch stopWatch = StopWatch.createStarted();

        //当天零点开始时间
        LocalDateTime dayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        //当天零点结束时间
        LocalDateTime dayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        log.info("起始时间:{}", dayStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.info("结束时间:{}", dayEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        String phone = "16602116670";
        phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        log.info("PHONE:{}", phone);
        String idCard = "411322198703013819";
        idCard = idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2");
        log.info("ID_CARD:{}", idCard);
        stopWatch.stop();
        log.info("RUN NanoTime:{};Millis:{}", stopWatch.getNanoTime(), stopWatch.getTime());

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    log.error("InterruptedException:{}", e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                t1.interrupt();
            }
        });
        t1.start();
        t2.start();

    }

}