package com.example.demo.listener;


import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.example.demo.constant.Common;
import com.example.demo.entity.model.AdminModel;
import com.example.demo.exception.DemoException;
import com.example.demo.service.rocketmq.AdminConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdminQueueListener implements MessageListener{

    @Autowired
    private AdminConsumerService adminConsumerService;

    @Override
    public Action consume(Message message, ConsumeContext context) {
        byte[] body = message.getBody();
        log.info("getContractsToSendIssue start  ! Topic:{},msgId:{}", message.getTopic(), message.getMsgID());
        String adminQueueBodyString = null;
        try {
            adminQueueBodyString = new String(body, Common.ENCODING_ROCKET_MQ);
            log.info("getContractsToSendIssue start  ! Topic:{},msgId:{},constraintQueueBodyString:{}",
                    message.getTopic(), message.getMsgID(),adminQueueBodyString);
            AdminModel adminModel = JSON.parseObject(adminQueueBodyString, AdminModel.class);
            this.adminConsumerService.sendAdminV1(adminModel);
        } catch (DemoException d) {
            log.info("getContractsToSendIssue  fail! Topic:{},msgId:{},code:{},msg:{},constraintQueueBodyString:{}", message.getTopic(),
                    message.getMsgID(), d.getCode(), d.getMsg(), adminQueueBodyString);
        } catch (Exception e) {
            log.info("getContractsToSendIssue fail! Topic:{},msgId:{},constraintQueueBodyString:{},e:{}", message.getTopic(),
                    message.getMsgID(), adminQueueBodyString,e);
        }
        //如果想测试消息重投的功能,可以将Action.CommitMessage 替换成Action.ReconsumeLater
        return Action.CommitMessage;
    }
}
