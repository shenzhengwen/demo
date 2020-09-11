package com.example.demo.service;

import com.example.demo.dao.AdminDao;
import com.example.demo.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl
{
    @Autowired
    private AdminDao adminDao;

    public Admin getAdminInfo(int aid){
        return this.adminDao.getAdminInfo(aid);
    }


}
