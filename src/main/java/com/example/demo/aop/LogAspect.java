/** Copyright  © Citic Trust, 2020 中信信托有限公司，版权所有  ©  2020 
 tae5Le2Choh5see2rieshaiQuooGhe7eeth0yahg3ud6eeb1ahsha6eloh2aiYai
*/



package com.example.demo.aop;

import com.example.demo.util.BusinessLogUtil;
import com.example.demo.util.HttpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;

@Component
@Aspect
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    BusinessLogUtil businessLogUtil;

    @Pointcut("execution(* com.example.demo.controller..*.*(..))")
    public void controller(){}

    @Around(value = "controller()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{

        HttpServletRequest httpServletRequest = HttpUtils.getRequest();
        Object[] args = pjp.getArgs();

        Object result;
        httpServletRequest.setAttribute("demo-controller-pjp", pjp);
        httpServletRequest.setAttribute("demo-controller-mst", Instant.now().toEpochMilli());
        if(args.length > 0){
            result = pjp.proceed(args);
        } else {
            result = pjp.proceed();
        }

        this.businessLogUtil.oplog(result);

        return result;
    }

    private String[] getRequestInfo(JoinPoint joinPoint){

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = signature.getDeclaringTypeName();
        String requestPath = "";
        String requestMethod = "REQUEST";

        try {
            requestPath = Class.forName(className).getDeclaredAnnotation(RequestMapping.class).value()[0];
        } catch (ClassNotFoundException e) {
        }

        if(method.getAnnotation(PostMapping.class) != null){
            requestPath += method.getAnnotation(PostMapping.class).value()[0];
            requestMethod = "POST";
        } else if(method.getAnnotation(GetMapping.class) != null){
            requestPath += method.getAnnotation(GetMapping.class).value()[0];
            requestMethod = "GET";
        } else if(method.getAnnotation(PutMapping.class) != null){
            requestPath += method.getAnnotation(PutMapping.class).value()[0];
            requestMethod = "PUT";
        } else if(method.getAnnotation(DeleteMapping.class) != null){
            requestPath += method.getAnnotation(DeleteMapping.class).value()[0];
            requestMethod = "DELETE";
        } else if(method.getAnnotation(PatchMapping.class) != null){
            requestPath += method.getAnnotation(PatchMapping.class).value()[0];
            requestMethod = "PATCH";
        } else {
            requestPath += method.getAnnotation(RequestMapping.class).value()[0];
        }

        return new String[]{requestPath, requestMethod};
    }


}
