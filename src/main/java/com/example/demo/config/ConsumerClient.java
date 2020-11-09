package com.example.demo.config;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.PropertyValueConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.example.demo.listener.AdminQueueListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ConsumerClient {

    @Autowired
    private MqConfig mqConfig;


    @Autowired
    private AdminQueueListener adminQueueListener;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ConsumerBean buildConsumer() {
        ConsumerBean consumerBean = new ConsumerBean();
        //配置文件
        Properties properties = mqConfig.getMqProperties();
        properties.setProperty(PropertyKeyConst.GROUP_ID, mqConfig.getGroupId());
        //将消费者线程数固定为20个 20为默认值
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, "1");
        // 集群订阅方式设置（不设置的情况下，默认为集群订阅方式）
        properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
        consumerBean.setProperties(properties);
        //订阅关系
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<>();

        //批量核算监听
        Subscription batchCheckSubscription = new Subscription();
        batchCheckSubscription.setTopic(mqConfig.getAdminTopic());
        batchCheckSubscription.setExpression(mqConfig.getTag());
        subscriptionTable.put(batchCheckSubscription, adminQueueListener);

        //订阅多个topic如上面设置
        consumerBean.setSubscriptionTable(subscriptionTable);

        return consumerBean;
    }


}
