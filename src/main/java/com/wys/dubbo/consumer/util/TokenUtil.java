package com.wys.dubbo.consumer.util;

import com.wys.dubbo.consumer.common.constant.BusinessOpportunityConstant;
import com.wys.dubbo.consumer.common.exception.BusinessException;
import com.wys.dubbo.consumer.common.exception.GlobalExceptionEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: token 工具类
 */
@Component
public class TokenUtil {

    /**
     * 请求头中获取token
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader(BusinessOpportunityConstant.TOKEN_HEADER);
        if (StringUtils.isNotEmpty(token) && token.startsWith(BusinessOpportunityConstant.TOKEN_HEADER_PREFIX)) {
            token = token.replace(BusinessOpportunityConstant.TOKEN_HEADER_PREFIX, "");
       } else {
            throw new BusinessException(GlobalExceptionEnum.NOT_AUTHORIZATION);
       }
        return token;
   }

    /**
     * 请求头中获取token不添加Bearer
     *
     * @param request
     * @return
     */
    public static String getTokenNotBearer(HttpServletRequest request) {
        String token = request.getHeader(BusinessOpportunityConstant.TOKEN_HEADER);
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(GlobalExceptionEnum.NO_LOGIN);
       }
        return token;
   }

    /**
     * 获取客户端IP
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || BusinessOpportunityConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
       }
        if (StringUtils.isBlank(ip) || BusinessOpportunityConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
       }
        if (StringUtils.isBlank(ip) || BusinessOpportunityConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
       }
        if (StringUtils.isBlank(ip) || BusinessOpportunityConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
       }
        if (StringUtils.isBlank(ip) || BusinessOpportunityConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
       }
        if (StringUtils.isBlank(ip)) {
            ip = BusinessOpportunityConstant.DEFAULT_IP;
       }
        return ip;
   }
}
