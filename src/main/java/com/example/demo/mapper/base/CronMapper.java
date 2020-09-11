package com.example.demo.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Cron;
import org.springframework.stereotype.Repository;

@Repository
public interface CronMapper extends BaseMapper<Cron>{

}
