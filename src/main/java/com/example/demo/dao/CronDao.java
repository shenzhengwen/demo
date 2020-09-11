package com.example.demo.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Cron;
import com.example.demo.mapper.base.AdminMapper;
import com.example.demo.mapper.base.CronMapper;
import com.example.demo.mapper.iservice.AdminIService;
import com.example.demo.mapper.iservice.CronIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CronDao extends ServiceImpl<CronMapper,Cron> implements CronIService
{
    @Autowired
    private CronMapper cronMapper;

    public Cron getCronInfo(String cronId)
    {
        QueryWrapper<Cron> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("cron_Id",cronId);
        return this.cronMapper.selectOne(queryWrapper);
    }
}
