package com.wys.dubbo.consumer.common.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @Classname CheckToken
 * @Description 验证token(主要用于解析token)
 * @Date 2020/1/6 14:24
 * @Created by Lizy
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
public @interface CheckToken {
}
