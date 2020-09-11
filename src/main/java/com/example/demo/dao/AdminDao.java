package com.example.demo.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Admin;
import com.example.demo.mapper.base.AdminMapper;
import com.example.demo.mapper.iservice.AdminIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDao extends ServiceImpl<AdminMapper,Admin> implements AdminIService
{
    @Autowired
    private AdminMapper adminMapper;

    public Admin getAdminInfo(int aid)
    {
        QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("aid",aid);
        return this.adminMapper.selectOne(queryWrapper);
    }
}
