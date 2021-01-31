package com.wys.dubbo.consumer.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName AvoidRepeatRequest
 * @Description: TODO 防止重复请求（默认1s内只能请求一次）
 * @Author 86133
 * @Date 2021/01/27
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AvoidRepeatRequest {
    long lockTime() default 1000;
}
