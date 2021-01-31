package com.wys.dubbo.consumer.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.wys.dubbo.consumer.common.exception.BusinessException;
import com.wys.dubbo.consumer.common.exception.GlobalExceptionEnum;
import com.wys.dubbo.consumer.util.RedisUtils;
import com.wys.dubbo.consumer.util.ThreadLocalUtil;
import com.wys.dubbo.consumer.util.TokenUtil;
import com.wys.dubbo.dto.response.OperationTokenRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 后台用户拦截器
 * @Author: boris
 * @Date: Created on 2020/12/9
 */
@Slf4j
@Component
public class OperateAuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            //获取token不校验bearer
            String token = TokenUtil.getTokenNotBearer(request);
            String jsonStr = (String) RedisUtils.get(token);
            if (StringUtils.isBlank(jsonStr)) {
                throw new BusinessException(GlobalExceptionEnum.NO_LOGIN);
            }
            OperationTokenRes operationTokenRes =JSONObject.parseObject(jsonStr,OperationTokenRes.class);

            //设置userId到request里，后续根据userId，获取用户信息
            ThreadLocalUtil.setLocalUserId(operationTokenRes.getId());
            ThreadLocalUtil.setLocalAccessToken(token);

            log.info("后台用户id:{}", operationTokenRes.getId());
        } catch (BusinessException e) {
            throw e;
        }
        return true;
    }

    /**
     * DispatcherServlet进行视图的渲染之后
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        ThreadLocalUtil.remove();
    }
}
