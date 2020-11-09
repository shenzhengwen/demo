package com.example.demo.schedule;

import com.example.demo.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

//@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling  // 2.开启定时任务
@Slf4j
public class TestSchedule {

    @Autowired
    private RedisService redisService;

    //3.添加定时任务
    //@Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate=5000)
    private void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        if (redisService.setNX("1", Thread.currentThread().getName(),  11L)) {
            //业务执行层
            log.info("获取锁cg");
            redisService.remove("1");
            log.info("解锁cg");
        } else {
            log.info("获取锁失败，等待下次调度");
        }
    }
}
