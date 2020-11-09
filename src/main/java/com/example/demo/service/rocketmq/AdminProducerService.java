package com.example.demo.service.rocketmq;

import com.example.demo.entity.AdminDO;
import com.example.demo.service.AdminServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminProducerService {

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @Autowired
    private SendRocketMqMsgService sendRocketMqMsgService;

    public void getAdminInfo() {

        List<AdminDO> adminDOList=this.adminServiceImpl.getAdminInfo();
        this.sendAdminListV1(adminDOList);
    }

    private void sendAdminListV1 (List<AdminDO> adminList) {
        //6.1 发送异步消息
        this.sendRocketMqMsgService.sendAdminList(adminList);

    }
}
