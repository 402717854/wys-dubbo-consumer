package com.wys.dubbo.consumer.config;

import com.wys.dubbo.consumer.util.ThreadLocalUtil;
import com.wys.dubbo.dto.request.BasicRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname OperationTokenAopConfig
 * @Description 专门用于token校验以及转换参数
 * @Date 2020/12/11 12:54
 * @Created by 20113370
 */
@Configuration
@Aspect
@Slf4j
public class OperationTokenAopConfig {
    /**
     * 切面路径地址凡是@CheckToken注解所处理的
     */
    @Pointcut("@within(com.wys.dubbo.consumer.common.annotation.CheckToken) || @annotation(com.wys.dubbo.consumer.common.annotation.CheckToken)")
    public void pointcut() {
        log.debug("token置换切面");
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
        log.debug("进入token置换切面");
        //替换用户id和token
        processInputArg(joinPoint.getArgs());

    }

    /**
     * 处理输入参数将userId和token写入bi'm
     *
     * @param args 入参列表
     */
    private void processInputArg(Object[] args) {
        for (Object arg : args) {
            log.debug("ARG原来为:" + arg);
            if (arg instanceof BasicRequest) {
                BasicRequest paramVO = (BasicRequest) arg;
                paramVO.setUserId(ThreadLocalUtil.getLocalUserId());
            }
        }
    }
}
