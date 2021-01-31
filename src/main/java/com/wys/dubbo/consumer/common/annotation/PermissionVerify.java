package com.wys.dubbo.consumer.common.annotation;

import java.lang.annotation.*;

/**
 * @Author: 权限校验注解
 * @Data: Created on 2020/7/15
 * @Description:
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionVerify {
    String[] permission() default {};
}
