/** Copyright  © Citic Trust, 2020 中信信托有限公司，版权所有  ©  2020 
 tae5Le2Choh5see2rieshaiQuooGhe7eeth0yahg3ud6eeb1ahsha6eloh2aiYai
*/



package com.example.demo.util;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 */
@Data
public final class LogModel {

    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    Long timeStamp;
    String sTime;
    String eTime;
    long duration;
    String serviceName;
    String className;
    String methodName;
    String pid;
    String traceId;

    Object inParam;
    Object outParam;
    String url;
    String type;
    Object userId;



    public static final class Builder {

        LogModel logModel = new LogModel();

        public Builder addTime(long sTime, long eTime) {
            LocalDateTime sDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(sTime), ZoneId.systemDefault());
            logModel.sTime = sDateTime.format(dateTimeFormatter);
            LocalDateTime eDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(sTime), ZoneId.systemDefault());
            logModel.eTime = eDateTime.format(dateTimeFormatter);
            logModel.duration = eTime - sTime;
            return this;
        }

        public Builder addServiceName(String serviceName) {
            logModel.serviceName = serviceName;
            return this;
        }

        public Builder addClassName(String className) {
            logModel.className = className;
            return this;
        }

        public Builder addMethod(String methodName) {
            logModel.methodName = methodName;
            return this;
        }

        public Builder addPid(String pid) {
            logModel.pid = pid;
            return this;
        }

        public Builder addTraceId(String traceId) {
            logModel.traceId = traceId;
            return this;
        }

        public Builder addUrl(String url) {
            logModel.url = url;
            return this;
        }

        public Builder addInParam(Object inParam) {
            if (Objects.isNull(inParam)) {
                inParam = new JSONObject();
            }
            logModel.inParam = inParam;
            return this;
        }

        public Builder addOutParam(Object outParam) {
            if (Objects.isNull(outParam)) {
                outParam = new JSONObject();
            }
            logModel.outParam = outParam;
            return this;
        }

        public Builder addTimeStamp(Long timeStamp) {
            logModel.timeStamp = timeStamp;
            return this;
        }

        public Builder addType(String type) {
            logModel.type = type;
            return this;
        }

        public Builder addUserId(String userId) {
            if (StringUtils.isEmpty(userId)) {
                userId = "";
            }
            logModel.userId = userId;
            return this;
        }

        public LogModel build() {
            return this.logModel;
        }

    }


    private final static String timeFormat(long times) {

        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(times), ZoneId.systemDefault());
        return dateTime.format(dateTimeFormatter);

    }

    @Override
    public String toString() {
        return "LogModel{" +
                "timeStamp=" + timeStamp +
                ", sTime='" + sTime + '\'' +
                ", eTime='" + eTime + '\'' +
                ", duration=" + duration +
                ", serviceName='" + serviceName + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", pid='" + pid + '\'' +
                ", traceId='" + traceId + '\'' +
                ", inParam=" + inParam +
                ", outParam=" + outParam +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", userId=" + userId +
                '}';
    }
}