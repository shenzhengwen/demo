/** Copyright  © Citic Trust, 2020 中信信托有限公司，版权所有  ©  2020 
 tae5Le2Choh5see2rieshaiQuooGhe7eeth0yahg3ud6eeb1ahsha6eloh2aiYai
*/



package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.model.BusinessModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class BusinessLogUtil {


    public final static Marker marker = MarkerFactory.getMarker("aspectLog");

    private final static Integer LOG_MAX_PARMA_LENGTH = 1024;

    private static String pid;

    private LogModel.Builder builder;

    @Value("${spring.application.name}")
    private String serviceName;

    @PostConstruct
    public void init() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);
        pid = name.split("@")[0];
    }

    /**
     * @param result
     */
    public final void oplog(Object result) {

        if (!log.isInfoEnabled()) {
            return;
        }
        HttpServletRequest request = HttpUtils.getRequest();
        if (Objects.isNull(request)) {
            return;
        }

        ProceedingJoinPoint pjp = (ProceedingJoinPoint) request.getAttribute("demo-controller-pjp");
        if (Objects.isNull(pjp)) {
            return;
        }
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = pjp.getArgs();
        long now = Instant.now().toEpochMilli();
        long begin = Objects.isNull(request.getAttribute("demo-controller-mst")) ?
                now : (Long) request.getAttribute("demo-controller-mst");
        long end = Instant.now().toEpochMilli();
        JSONObject inputParam = new JSONObject();
        if (args.length > 0) {
            int size = parameterNames.length;
            String value;
            for (int i = 0; i < size - 1; ++i) {
                if (args[i] instanceof BusinessModel) {
                    inputParam.put(parameterNames[i], JSON.toJSON(args[i]));
                    continue;
                }
                value = args[i] != null ? args[i].toString() : "null";
                if (value.length() > LOG_MAX_PARMA_LENGTH) {
                    continue;
                }
                inputParam.put(parameterNames[i], value);
            }

            value = args[size - 1] != null ? args[size - 1].toString() : "null";
            if (value.length() <= LOG_MAX_PARMA_LENGTH) {
                inputParam.put(parameterNames[size - 1], value);
            }
        }

        this.builder = new LogModel.Builder()
                .addTimeStamp(begin)
                .addClassName(signature.getDeclaringTypeName())
                .addTime(begin, end)
                .addPid(pid)
                .addServiceName(serviceName)
                .addMethod(signature.getMethod().getName())
                .addUrl(request.getRequestURI())
                .addInParam(inputParam)
                .addOutParam(result);

        LogModel logModel = builder.build();
        log.info(marker, "{}", JSONObject.toJSONString(logModel));
    }

    public final void businesslog(Object result) {

        try {
            if (!log.isInfoEnabled()) {
                return;
            }
            HttpServletRequest request = HttpUtils.getRequest();
            if (Objects.isNull(request)) {
                return;
            }

            ProceedingJoinPoint pjp = (ProceedingJoinPoint) request.getAttribute("demo-business-pjp");
            if (Objects.isNull(pjp)) {
                return;
            }
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method method = signature.getMethod();
            Object[] args = pjp.getArgs();
            long now = Instant.now().toEpochMilli();
            long begin = Objects.isNull(request.getAttribute("demo-business-mst")) ?
                    now : (Long) request.getAttribute("demo-business-mst");
            long end = Instant.now().toEpochMilli();
            Map<String, Object> inputParam = new HashMap<>(args.length);
            if (args.length > 0) {
                int size = args.length;
                String value;
                for (int i = 0; i < size - 1; ++i) {
                    value = args[i] != null ? args[i].toString() : "null";
                    if (value.length() > LOG_MAX_PARMA_LENGTH) {
                        continue;
                    }
                    inputParam.put(args.getClass().getName(), value);
                }

                value = args[size - 1] != null ? args[size - 1].toString() : "null";
                if (value.length() <= LOG_MAX_PARMA_LENGTH) {
                    inputParam.put(args.getClass().getName(), value);
                }
            }

            LogModel.Builder builder = new LogModel.Builder()
                    .addTimeStamp(begin)
                    .addClassName(method.getDeclaringClass().getName())
                    .addTime(begin, end)
                    .addPid(pid)
                    .addServiceName(serviceName)
                    .addMethod(method.getName())
                    .addUrl(request.getRequestURI())
                    .addInParam(inputParam)
                    .addOutParam(result);

            LogModel logModel = builder.build();
            log.info(marker, "{}", JSONObject.toJSONString(logModel));

        } catch (Exception e) {
            log.error("打印business日志失败", e);
        }
    }

    public final void builderType(String type) {
        this.builder.addType(type);
    }

}
