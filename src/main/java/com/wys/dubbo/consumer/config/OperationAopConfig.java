package com.wys.dubbo.consumer.config;


import com.wys.dubbo.consumer.common.exception.BusinessException;
import com.wys.dubbo.consumer.common.exception.GlobalExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Description: AopConfig 切面校验 捕获异常
 * @Author: 86133
 * @Date: 2021/01/27
 */
@Configuration
@Aspect
@Slf4j
public class OperationAopConfig {

    /**
     * 切面路径地址凡是RestController注解所处理的
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)||@within(org.springframework.stereotype.Controller)")
    public void pointcut() {
        log.debug("切面");
   }

    //设置扫描所有切面并且被注解修饰的方法环绕通知
    @Around("pointcut()")
    public Object simpleAop(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("进入切面");
        String methodName=joinPoint.getSignature().getName();
        String className=joinPoint.getSignature().getDeclaringTypeName();
        Long befor = System.currentTimeMillis();
        // 调用原有的方法
        try {
            Object o = joinPoint.proceed();
            Long after = System.currentTimeMillis();
            log.info("调用方法结束===================共耗时：" + (after - befor) + "毫秒");
            log.debug("方法返回：return:====================" + o);
            return o;
       }catch (BusinessException e){
            log.error("[{}]调用方法[{}]出现运行时异常:",className,methodName,e);
            throw e;
       }catch(Exception e){
            log.error("[{}]调用方法[{}]出现未知系统异常:",className,methodName,e);
            throw new BusinessException(GlobalExceptionEnum.DUBBO_ERROR);
       }
   }

    /**
     * @return void
     * @Description 前置拦截
     * @Param joinPoint
     * @Date 12:47 2020/12/11
     * @Author lizy
     **/
    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        // 我们从请求的上下文中获取request，记录请求的内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        log.debug("请求路径 : " + request.getRequestURL());
        log.debug("请求方式 : " + request.getMethod());
        log.debug("方法名 : " + joinPoint.getSignature().getName());
        log.debug("类路径 : " + joinPoint.getSignature().getDeclaringTypeName());
        log.debug("参数 : " + Arrays.toString(joinPoint.getArgs()));
        Long befor = System.currentTimeMillis();
        log.debug("方法开始时间：【{}】", befor);

   }

}
