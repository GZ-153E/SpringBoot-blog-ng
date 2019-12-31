package com.lrm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.lrm.web.*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        //获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //请求地址
        String url = request.getRequestURL().toString();
        //请求IP地址
        String ip = request.getRemoteAddr();
        //请求的数据
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Result : {}",requestLog);
    }
    @After("log()")
    public void doAfter(){
        logger.info("----------after------------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRuturn(Object result) {
        logger.info("Result : {}",result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] ages;

        public RequestLog(String url, String ip, String classMethod, Object[] ages) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.ages = ages;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", ages=" + Arrays.toString(ages) +
                    '}';
        }
    }
}
