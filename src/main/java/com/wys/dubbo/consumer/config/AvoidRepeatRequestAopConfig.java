package com.wys.dubbo.consumer.config;

import com.alibaba.fastjson.JSON;
import com.wys.dubbo.consumer.common.annotation.AvoidRepeatRequest;
import com.wys.dubbo.consumer.common.constant.BusinessOpportunityConstant;
import com.wys.dubbo.consumer.common.response.ExecuteResult;
import com.wys.dubbo.consumer.util.Md5Util;
import com.wys.dubbo.consumer.util.RedisUtils;
import com.wys.dubbo.consumer.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @ClassName AvoidRepeatRequestAspectConfig
 * @Description: TODO
 * @Author wys
 * @Date 2021/1/6-11:29
 * @Version V1.0
 **/
@Slf4j
@Aspect
@Component
public class AvoidRepeatRequestAopConfig {

    /**
     * 切点（匹配规则为：@AvoidRepeatRequest的所有方法）
     */
    @Pointcut("@annotation(com.wys.dubbo.consumer.common.annotation.AvoidRepeatRequest)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        AvoidRepeatRequest avoidRepeatRequest = method.getAnnotation(AvoidRepeatRequest.class);
        long lockTime = avoidRepeatRequest.lockTime();

        HttpServletRequest request = httpServletRequest();
        String path = request.getServletPath();
        String token = request.getHeader(BusinessOpportunityConstant.TOKEN_HEADER);
        if(StringUtils.isEmpty(token)){
            String ipAddress = TokenUtil.getIpAddress(request);
            Object[] args = pjp.getArgs();
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(ipAddress);
            for (int i = 0; i < args.length; i++) {
                Object param=args[i];
                String string = JSON.toJSONString(param);
                stringBuffer.append(string);
            }
            token = Md5Util.getMd5String(stringBuffer.toString());
        }
        String key = getKey(token, path);
        String clientId = getClientId();
        try{
            boolean lock = RedisUtils.lock(key, clientId, lockTime);
            if(!lock){
                return ExecuteResult.fail("请求过于频繁，请稍后再试");
            }
            log.info("tryLock success, key = [{}], clientId = [{}]", key, clientId);
            Object result = pjp.proceed();
            return result;
        }finally {
            RedisUtils.safeUnLock(key,clientId);
            log.info("releaseLock success, key = [{}], clientId = [{}]", key, clientId);
        }
    }
    /**
     * 获得request对象
     *
     * @return
     */
    private HttpServletRequest httpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }
    /**
     * 获得请求key
     *
     * @param token
     * @param path
     * @return
     */
    private String getKey(String token, String path) {
        return token +":"+ path;
    }
    /**
     * 获得uuid
     *
     * @return
     */
    private String getClientId() {
        return UUID.randomUUID().toString();
    }
}
