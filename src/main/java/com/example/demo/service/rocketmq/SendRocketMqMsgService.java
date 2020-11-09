package com.example.demo.service.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.example.demo.config.MqConfig;
import com.example.demo.constant.ResponseCode;
import com.example.demo.entity.AdminDO;
import com.example.demo.exception.DemoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SendRocketMqMsgService {

    @Autowired
    private ProducerBean producer;

    @Autowired
    private MqConfig mqConfig;

    /**
     * 发送admin分发
     */
    public void sendAdminList(List<AdminDO> adminDOList) {
        try {
            adminDOList.parallelStream().forEach(adminDO -> {
                String adminBodyString = JSONObject.toJSONString(adminDO);
                Message message = new Message(mqConfig.getAdminTopic(), mqConfig.getTag(), adminBodyString.getBytes());
                try {
                    // 设置代表消息的业务关键属性，请尽可能全局唯一
                    // 以方便您在无法正常收到消息情况下，可通过MQ 控制台查询消息并补发
                    // 注意：不设置也不会影响消息正常收发
                    message.setKey(adminDO.getAid() + "#" + adminDO.getAdminName());
                    SendResult sendResult = producer.send(message);
                    assert sendResult != null;
                    log.info("Send mq timer message success! Topic:{},msgId:{},adminBodyString:{}",
                            mqConfig.getAdminTopic(), sendResult.getMessageId(), adminBodyString);
                } catch (Exception e) {
                    log.error("sendAdminList fail e:{}", e.getMessage());
                }
            });
        } catch (Exception e) {
            // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
            log.error("Send mq timer message fail e:{}", e);
            throw new DemoException(ResponseCode.SEND_CONTRACT_FAIL.getCode(), ResponseCode.SEND_CONTRACT_FAIL.getMsg());
        }
    }

}
