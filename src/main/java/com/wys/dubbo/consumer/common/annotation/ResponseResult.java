package com.wys.dubbo.consumer.common.annotation;

import java.lang.annotation.*;

/**
 * @ClassName AvoidRepeatRequest
 * @Description: TODO 统一处理controller响应结果
 * @Author 86133
 * @Date 2021/01/27
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface ResponseResult {
}
