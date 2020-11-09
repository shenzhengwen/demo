package com.example.demo.service;

import com.example.demo.dao.CronDao;
import com.example.demo.entity.CronDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CronServiceImpl
{
    @Autowired
    private CronDao cronDao;

    public CronDO getCronInfo(String cronId){
        return this.cronDao.getCronInfo(cronId);
    }

    public int inertCron(CronDO cronDO){
        return this.cronDao.insertCron(cronDO);
    }

}
