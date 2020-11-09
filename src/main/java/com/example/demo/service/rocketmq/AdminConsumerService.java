package com.example.demo.service.rocketmq;

import com.example.demo.entity.AdminDO;
import com.example.demo.entity.model.AdminModel;
import com.example.demo.service.AdminServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class AdminConsumerService {

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @Autowired
    private SendRocketMqMsgService sendRocketMqMsgService;

    public void sendAdminV1 (AdminModel adminModel) {
        log.info("消费的admin id为",adminModel.getAid());
    }
}
