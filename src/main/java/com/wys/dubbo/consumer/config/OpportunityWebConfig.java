package com.wys.dubbo.consumer.config;

import com.wys.dubbo.consumer.common.interceptor.OperateAuthInterceptor;
import com.wys.dubbo.consumer.common.interceptor.ResponseResultInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: WebMvcConfigurer
 * @Author: boris
 * @Date: Created on 2020/12/9
 */
@Configuration
public class OpportunityWebConfig implements WebMvcConfigurer {

    @Autowired
    private OperateAuthInterceptor operateAuthInterceptor;
    @Autowired
    private ResponseResultInterceptor responseResultInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry){

        registry.addInterceptor(responseResultInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");

        registry.addInterceptor(operateAuthInterceptor)
                .addPathPatterns("/operation/**")
                .excludePathPatterns("/operation/sysUserManage/login",
                        "/operation/sysUserManage/register")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");   // 排除swagger地址;
    }


    //排除swagger请求
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger/**").addResourceLocations("classpath:/static/swagger/");
    }


}
