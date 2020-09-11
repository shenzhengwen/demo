package com.example.demo.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;


//@Component
public class MailServiceUtil {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceUtil.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    /**
     * 发送文本邮件
     */
    private static void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("18986822156@189.cn");
        message.setTo("2280168205@qq.com");
        message.setSubject("hello this is simple mail");
        message.setText("hello this is simple mail");

        try {
            JavaMailSender mailSender = new JavaMailSenderImpl();
            mailSender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }

    }
}