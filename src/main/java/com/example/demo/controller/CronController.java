package com.example.demo.controller;

import com.example.demo.constant.ResponseCode;
import com.example.demo.entity.AdminDO;
import com.example.demo.entity.CronDO;
import com.example.demo.entity.dto.ApiResponse;
import com.example.demo.exception.DbException;
import com.example.demo.exception.DemoException;
import com.example.demo.service.CronServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cron")
@Api(value = "系统配置接口")
@Slf4j
public class CronController {

    @Autowired
    private CronServiceImpl cronServiceImpl;

    @ResponseBody
    @RequestMapping("/insertCron")
    @ApiOperation("新增配置")
    public ApiResponse insertCron(@RequestBody CronDO cronDO) {
        ApiResponse<AdminDO> response = new ApiResponse<>();

        try {
            Assert.notNull(cronDO, "param name must not be null");

        } catch (IllegalArgumentException ex) {
            response.setCodeAndMessage(ResponseCode.ILLEGAL_PARAM);
            return response;
        }
        try {
            response.setCodeAndMessage(ResponseCode.OK);
            this.cronServiceImpl.inertCron(cronDO);
        } catch (DbException dbex) {
            log.error("database access error.", dbex);
            response.setCodeAndMessage(ResponseCode.DB_EXCEPTION);
        } catch (DemoException ex) {
            response.setCodeAndMessage(ResponseCode.DEMO_SERVICE_EXCEPTION);
        } catch (Exception e) {
            response.setCodeAndMessage(ResponseCode.UNKNOWN_EXCEPTION);
        }
        return response;
    }
}
