package com.wys.dubbo.consumer.config;

import com.wys.dubbo.consumer.common.exception.GlobalExceptionEnum;
import com.wys.dubbo.consumer.common.annotation.PermissionVerify;
import com.wys.dubbo.consumer.common.exception.BusinessException;
import com.wys.dubbo.consumer.util.ThreadLocalUtil;
import com.wys.dubbo.result.RpcExecuteResult;
import com.wys.dubbo.service.DubboMenuServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 权限校验器
 *
 * @Author: boris
 * @Data: Created on 2020/7/15
 * @Description:
 */
@Aspect
@Component
@Slf4j
public class PermissionVerifyAopConfig {

    /**
     * 菜单信息
     */
    @Reference
    private DubboMenuServiceClient dubboMenuServiceClient;

    //切入点表达式决定了用注解方式的方法切还是针对某个路径下的所有类和方法进行切，方法必须是返回void类型
    @Pointcut(value = "@annotation(com.wys.dubbo.consumer.common.annotation.PermissionVerify)")
    private void permissionCheckCut() {
    }


    //定义了切面的处理逻辑。即方法上加了@PermissionCheck
    @Before("permissionCheckCut()")
    public void before(JoinPoint joinPoint) throws Throwable {

        Integer userId = ThreadLocalUtil.getLocalUserId();
        Signature signature = joinPoint.getSignature();

        //权限校验
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();

        //获取方法上注解中表明的权限
        PermissionVerify permission = targetMethod.getAnnotation(PermissionVerify.class);
        String[] permissionArr = permission.permission();

        if (permissionArr == null || permissionArr.length <= 0) {
            //所需权限为空返回
            return;
        }
        //解析需要的权限
        List<String> permissionList = Arrays.asList(permissionArr);

        //获取用户已有的权限
        Set<String> userPerms=null;
        RpcExecuteResult<Set<String>> result = dubboMenuServiceClient.queryPermsMenuByUserId(userId);
        if(result.isSuccess()){
            userPerms = result.getData();
        }

        if (userPerms == null || userPerms.isEmpty()) {
            throw new BusinessException(GlobalExceptionEnum.PERMISSION_DENIED);
        }

        //判断用户是否有所需的权限
        for (String permissionStr : permissionList) {
            if (userPerms.contains(permissionStr)) {
                return;
            }
        }

        //没有抛出异常
        throw new BusinessException(GlobalExceptionEnum.PERMISSION_DENIED);
    }

}
