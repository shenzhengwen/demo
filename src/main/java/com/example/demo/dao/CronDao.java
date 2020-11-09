package com.example.demo.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.CronDO;
import com.example.demo.mapper.base.CronMapper;
import com.example.demo.mapper.iservice.CronIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CronDao extends ServiceImpl<CronMapper,CronDO> implements CronIService
{
    @Autowired
    private CronMapper cronMapper;

    public CronDO getCronInfo(String cronId)
    {
        QueryWrapper<CronDO> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("cron_Id",cronId);
        return this.cronMapper.selectOne(queryWrapper);
    }

    public int insertCron(CronDO cronDO)
    {
        return this.cronMapper.insert(cronDO);
    }
}
