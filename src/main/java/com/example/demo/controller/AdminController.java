package com.example.demo.controller;

import com.example.demo.constant.ResponseCode;
import com.example.demo.entity.AdminDO;
import com.example.demo.entity.dto.ApiResponse;
import com.example.demo.exception.DbException;
import com.example.demo.exception.DemoException;
import com.example.demo.service.AdminServiceImpl;
import com.example.demo.service.rocketmq.AdminProducerService;
import com.example.demo.util.BusinessLogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Api(value = "管理员相关接口")
@Slf4j
public class AdminController {

    @Autowired
    BusinessLogUtil businessLogUtil;

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @Autowired
    private AdminProducerService adminProducerService;

    @ResponseBody
    @RequestMapping("/getAdminInfo")
    @ApiOperation("查询用户")
        public ApiResponse getAdminInfo(@RequestParam(value = "aid") int aid)
        {
            ApiResponse<AdminDO> response = new ApiResponse<>();

            try {
                Assert.notNull(aid, "param name must not be null");
            } catch (IllegalArgumentException ex) {
//            log.info(ex.getMessage());
                response.setCodeAndMessage(ResponseCode.ILLEGAL_PARAM);
                return response;
            }

            try {
                response.setCodeAndMessage(ResponseCode.OK);
                AdminDO adminDO=this.adminServiceImpl.getAdminInfo(aid);
                response.setResult(adminDO);
            } catch (DbException dbex) {
                log.error("database access error.", dbex);
                response.setCodeAndMessage(ResponseCode.DB_EXCEPTION);
            } catch (DemoException ex) {
                response.setCodeAndMessage(ResponseCode.DEMO_SERVICE_EXCEPTION);
            } catch (Exception e) {
                response.setCodeAndMessage(ResponseCode.UNKNOWN_EXCEPTION);
            }

//      step 3 业务日志记录（如果需要的话）
//      BusinessModel businessModel = new BusinessModel();
//      this.businessLogUtil.oplog(businessModel);
        return response;

    }

    @ResponseBody
    @RequestMapping("/findAdminInfo")
    @ApiOperation("查询用户")
    public ApiResponse findAdminInfo(@RequestBody AdminDO adminDO)
    {
        ApiResponse<List<AdminDO>> response = new ApiResponse<>();

        try {
            Assert.notNull(adminDO, "param name must not be null");
        } catch (IllegalArgumentException ex) {
            response.setCodeAndMessage(ResponseCode.ILLEGAL_PARAM);
            return response;
        }

        try {
            response.setCodeAndMessage(ResponseCode.OK);
            this.adminProducerService.getAdminInfo();
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

    @ResponseBody
    @RequestMapping("/insertAdminInfo")
    @ApiOperation("新增用户")
    public ApiResponse insertAdminInfo(@RequestBody AdminDO adminDO)
    {
        ApiResponse<AdminDO> response = new ApiResponse<>();

        try {
            Assert.notNull(adminDO, "param name must not be null");
            Assert.notNull(adminDO.getAid(), "param name must not be null");
            Assert.notNull(adminDO.getAdminType(), "param name must not be null");
        } catch (IllegalArgumentException ex) {
            response.setCodeAndMessage(ResponseCode.ILLEGAL_PARAM);
            return response;
        }
        try {
            response.setCodeAndMessage(ResponseCode.OK);
            this.adminServiceImpl.insertAdminInfo(adminDO);
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

    @ResponseBody
    @RequestMapping("/updateAdminInfo")
    @ApiOperation("新增用户")
    public ApiResponse updateAdminInfo(@RequestBody String aid)
    {
        ApiResponse<AdminDO> response = new ApiResponse<>();

        try {
            Assert.notNull(aid, "param name must not be null");
        } catch (IllegalArgumentException ex) {
            response.setCodeAndMessage(ResponseCode.ILLEGAL_PARAM);
            return response;
        }
        try {
            response.setCodeAndMessage(ResponseCode.OK);
            this.adminServiceImpl.updateAdminInfo(aid);
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
