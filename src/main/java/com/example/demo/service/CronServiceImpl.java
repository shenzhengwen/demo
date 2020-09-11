package com.example.demo.service;

import com.example.demo.dao.CronDao;
import com.example.demo.entity.Cron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CronServiceImpl
{
    @Autowired
    private CronDao cronDao;

    public Cron getCronInfo(String cronId){
        return this.cronDao.getCronInfo(cronId);
    }


}
