package com.example.demo.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.constant.Common;
import com.example.demo.entity.AdminDO;
import com.example.demo.mapper.base.AdminMapper;
import com.example.demo.mapper.iservice.AdminIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDao extends ServiceImpl<AdminMapper,AdminDO> implements AdminIService
{
    @Autowired
    private AdminMapper adminMapper;

    public AdminDO getAdminInfo(int aid)
    {
        QueryWrapper<AdminDO> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("aid",aid);
        return this.adminMapper.selectOne(queryWrapper);
    }

    public int insertAdminInfo(AdminDO adminDO)
    {
        return this.adminMapper.insert(adminDO);
    }

    public List<AdminDO> findAllAdminInfo(){
//        QueryWrapper<AdminDO> queryWrapper=new QueryWrapper<>();
        return this.adminMapper.selectList(null);
    }
    public int updateAdminInfo(String aid){
        try {
            AdminDO adminDO=new AdminDO();
            adminDO.setAdminType(String.valueOf(Common.DEFAULT_VALUE));
            UpdateWrapper<AdminDO> updateWrapper=new UpdateWrapper<>();
            updateWrapper.eq("aid",aid);
            return this.adminMapper.update(adminDO,updateWrapper);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }


}
