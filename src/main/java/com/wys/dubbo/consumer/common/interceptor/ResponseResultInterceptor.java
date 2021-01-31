package com.wys.dubbo.consumer.common.interceptor;

import com.wys.dubbo.consumer.common.annotation.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @ClassName ResponseResultHandle
 * @Description: TODO 拦截请求，判断是否需要返回值包装，设置一个属性标记
 * @Author 86133
 * @Date 2021/01/27
 **/
@Slf4j
@Aspect
@Component
public class ResponseResultInterceptor extends HandlerInterceptorAdapter {

    private static final String RESPONSE_RESULT_ANN="RESPONSE_RESULT_ANN";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            Class<?> clazz = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            //判断是否在类对象上面加了注解
            if(clazz.isAnnotationPresent(ResponseResult.class)){
                //设置此请求返回体，需要包装，在ResponseBodyAdvice接口进行判断
                request.setAttribute(RESPONSE_RESULT_ANN,clazz.getAnnotation(ResponseResult.class));
            }
            if(method.isAnnotationPresent(ResponseResult.class)){
                //设置此请求返回体，需要包装，在ResponseBodyAdvice接口进行判断
                request.setAttribute(RESPONSE_RESULT_ANN,clazz.getAnnotation(ResponseResult.class));
            }
       }
        return true;
   }
}
