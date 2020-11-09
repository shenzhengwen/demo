package com.example.demo.service;

import com.example.demo.dao.AdminDao;
import com.example.demo.entity.AdminDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminServiceImpl
{
    @Autowired
    private AdminDao adminDao;

    public AdminDO getAdminInfo(int aid){
        return this.adminDao.getAdminInfo(aid);
    }

    public List<AdminDO> getAdminInfo(){

        return this.adminDao.findAllAdminInfo();
    }
    public int insertAdminInfo(AdminDO adminDO) {
        return this.adminDao.insertAdminInfo(adminDO);
    }

    public int updateAdminInfo(String aid) {
        return this.adminDao.updateAdminInfo(aid);
    }
}
