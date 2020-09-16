package com.example.demo.controller;

import com.example.demo.entity.Admin;
import com.example.demo.service.AdminServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Api(value = "管理员相关接口")
@Slf4j
public class AdminController {

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @ResponseBody
    @RequestMapping("/getAdminInfo")
    @ApiOperation("查询用户")
    public Admin getAdminInfo(@RequestParam(value = "aid") int aid)
    {
        log.info("调用----根据用户id查询");
        return adminServiceImpl.getAdminInfo(aid);

    }
}
